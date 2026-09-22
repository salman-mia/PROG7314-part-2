using CampusConnect.API.DTOs.Events;

namespace CampusConnect.API.Interfaces;

public interface IEventService
{
    Task<IEnumerable<EventResponse>> GetAllAsync();
    Task<EventResponse?> GetByIdAsync(int id);
    Task<EventResponse> CreateAsync(CreateEventRequest request, int userId);
    Task<EventResponse?> UpdateAsync(int id, UpdateEventRequest request, int userId);
    Task<bool> DeleteAsync(int id, int userId);
    Task<bool> RsvpAsync(int eventId, int userId);
    Task<bool> CancelRsvpAsync(int eventId, int userId);
}
