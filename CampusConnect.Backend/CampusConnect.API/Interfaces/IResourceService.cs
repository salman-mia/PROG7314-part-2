using CampusConnect.API.DTOs.Resources;

namespace CampusConnect.API.Interfaces;

public interface IResourceService
{
    Task<IEnumerable<ResourceResponse>> GetAllAsync();
    Task<ResourceResponse?> GetByIdAsync(int id);
    Task<ResourceResponse> CreateAsync(CreateResourceRequest request);
    Task<ResourceResponse?> UpdateAsync(int id, UpdateResourceRequest request);
    Task<bool> DeleteAsync(int id);
}
