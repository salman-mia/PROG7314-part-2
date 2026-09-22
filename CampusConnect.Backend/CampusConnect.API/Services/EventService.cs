using CampusConnect.API.Data;
using CampusConnect.API.DTOs.Events;
using CampusConnect.API.Interfaces;
using CampusConnect.API.Models;
using Microsoft.EntityFrameworkCore;

namespace CampusConnect.API.Services;

public class EventService : IEventService
{
    private readonly CampusConnectDbContext _db;
    private readonly ILogger<EventService> _logger;
    public EventService(CampusConnectDbContext db, ILogger<EventService> logger) { _db = db; _logger = logger; }

    public async Task<IEnumerable<EventResponse>> GetAllAsync() => await _db.Events.AsNoTracking().OrderBy(e => e.EventDate)
        .Select(e => new EventResponse(e.Id, e.Title, e.Description, e.Location, e.EventDate, e.CreatedBy, e.CreatedAt, e.RSVPs.Count)).ToListAsync();

    public async Task<EventResponse?> GetByIdAsync(int id) => await _db.Events.AsNoTracking().Where(e => e.Id == id)
        .Select(e => new EventResponse(e.Id, e.Title, e.Description, e.Location, e.EventDate, e.CreatedBy, e.CreatedAt, e.RSVPs.Count)).SingleOrDefaultAsync();

    public async Task<EventResponse> CreateAsync(CreateEventRequest request, int userId)
    {
        var entity = new Event { Title = request.Title.Trim(), Description = request.Description.Trim(), Location = request.Location.Trim(), EventDate = request.EventDate, CreatedBy = userId };
        _db.Events.Add(entity); await _db.SaveChangesAsync();
        _logger.LogInformation("Event created: {EventId} by {UserId}", entity.Id, userId);
        return new EventResponse(entity.Id, entity.Title, entity.Description, entity.Location, entity.EventDate, entity.CreatedBy, entity.CreatedAt, 0);
    }

    public async Task<EventResponse?> UpdateAsync(int id, UpdateEventRequest request, int userId)
    {
        var entity = await _db.Events.FindAsync(id);
        if (entity is null || entity.CreatedBy != userId) return null;
        entity.Title = request.Title.Trim(); entity.Description = request.Description.Trim(); entity.Location = request.Location.Trim(); entity.EventDate = request.EventDate;
        await _db.SaveChangesAsync();
        return await GetByIdAsync(id);
    }

    public async Task<bool> DeleteAsync(int id, int userId)
    {
        var entity = await _db.Events.FindAsync(id);
        if (entity is null || entity.CreatedBy != userId) return false;
        _db.Events.Remove(entity); await _db.SaveChangesAsync(); return true;
    }

    public async Task<bool> RsvpAsync(int eventId, int userId)
    {
        if (!await _db.Events.AnyAsync(e => e.Id == eventId) || await _db.EventRSVPs.AnyAsync(r => r.EventId == eventId && r.UserId == userId)) return false;
        _db.EventRSVPs.Add(new EventRSVP { EventId = eventId, UserId = userId }); await _db.SaveChangesAsync(); return true;
    }

    public async Task<bool> CancelRsvpAsync(int eventId, int userId)
    {
        var rsvp = await _db.EventRSVPs.SingleOrDefaultAsync(r => r.EventId == eventId && r.UserId == userId);
        if (rsvp is null) return false;
        _db.EventRSVPs.Remove(rsvp); await _db.SaveChangesAsync(); return true;
    }
}
