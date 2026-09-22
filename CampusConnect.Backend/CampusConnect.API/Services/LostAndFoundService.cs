using CampusConnect.API.Data;
using CampusConnect.API.DTOs.LostAndFound;
using CampusConnect.API.Interfaces;
using CampusConnect.API.Models;
using Microsoft.EntityFrameworkCore;

namespace CampusConnect.API.Services;

public class LostAndFoundService : ILostAndFoundService
{
    private readonly CampusConnectDbContext _db;
    public LostAndFoundService(CampusConnectDbContext db) => _db = db;

    public async Task<IEnumerable<LostAndFoundResponse>> GetAllAsync() => await _db.LostAndFoundItems.AsNoTracking().OrderByDescending(i => i.CreatedAt)
        .Select(i => new LostAndFoundResponse(i.Id, i.Title, i.Description, i.Location, i.Type, i.Status, i.PostedBy, i.CreatedAt)).ToListAsync();

    public async Task<LostAndFoundResponse?> GetByIdAsync(int id) => await _db.LostAndFoundItems.AsNoTracking().Where(i => i.Id == id)
        .Select(i => new LostAndFoundResponse(i.Id, i.Title, i.Description, i.Location, i.Type, i.Status, i.PostedBy, i.CreatedAt)).SingleOrDefaultAsync();

    public async Task<LostAndFoundResponse> CreateAsync(CreateLostAndFoundRequest request, int userId)
    {
        var entity = new LostAndFoundItem { Title = request.Title.Trim(), Description = request.Description.Trim(), Location = request.Location.Trim(), Type = request.Type, PostedBy = userId };
        _db.LostAndFoundItems.Add(entity); await _db.SaveChangesAsync();
        return new LostAndFoundResponse(entity.Id, entity.Title, entity.Description, entity.Location, entity.Type, entity.Status, entity.PostedBy, entity.CreatedAt);
    }

    public async Task<LostAndFoundResponse?> UpdateAsync(int id, UpdateLostAndFoundRequest request, int userId)
    {
        var entity = await _db.LostAndFoundItems.FindAsync(id); if (entity is null || entity.PostedBy != userId) return null;
        entity.Title = request.Title.Trim(); entity.Description = request.Description.Trim(); entity.Location = request.Location.Trim(); entity.Type = request.Type;
        await _db.SaveChangesAsync(); return await GetByIdAsync(id);
    }

    public async Task<bool> DeleteAsync(int id, int userId)
    {
        var entity = await _db.LostAndFoundItems.FindAsync(id); if (entity is null || entity.PostedBy != userId) return false;
        _db.LostAndFoundItems.Remove(entity); await _db.SaveChangesAsync(); return true;
    }
}
