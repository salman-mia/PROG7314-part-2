using CampusConnect.API.DTOs.LostAndFound;

namespace CampusConnect.API.Interfaces;

public interface ILostAndFoundService
{
    Task<IEnumerable<LostAndFoundResponse>> GetAllAsync();
    Task<LostAndFoundResponse?> GetByIdAsync(int id);
    Task<LostAndFoundResponse> CreateAsync(CreateLostAndFoundRequest request, int userId);
    Task<LostAndFoundResponse?> UpdateAsync(int id, UpdateLostAndFoundRequest request, int userId);
    Task<bool> DeleteAsync(int id, int userId);
}
