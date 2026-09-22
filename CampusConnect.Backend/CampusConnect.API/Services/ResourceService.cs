using CampusConnect.API.Data;
using CampusConnect.API.DTOs.Resources;
using CampusConnect.API.Interfaces;
using CampusConnect.API.Models;
using Microsoft.EntityFrameworkCore;

namespace CampusConnect.API.Services;

public class ResourceService : IResourceService
{
    private readonly CampusConnectDbContext _db;
    public ResourceService(CampusConnectDbContext db) => _db = db;

    public async Task<IEnumerable<ResourceResponse>> GetAllAsync() => await _db.AcademicResources.AsNoTracking().OrderBy(r => r.ModuleCode)
        .Select(r => new ResourceResponse(r.Id, r.Title, r.Description, r.ResourceUrl, r.ModuleCode, r.CreatedAt)).ToListAsync();

    public async Task<ResourceResponse?> GetByIdAsync(int id) => await _db.AcademicResources.AsNoTracking().Where(r => r.Id == id)
        .Select(r => new ResourceResponse(r.Id, r.Title, r.Description, r.ResourceUrl, r.ModuleCode, r.CreatedAt)).SingleOrDefaultAsync();

    public async Task<ResourceResponse> CreateAsync(CreateResourceRequest request)
    {
        var entity = new AcademicResource { Title = request.Title.Trim(), Description = request.Description.Trim(), ResourceUrl = request.ResourceUrl.Trim(), ModuleCode = request.ModuleCode.Trim().ToUpperInvariant() };
        _db.AcademicResources.Add(entity); await _db.SaveChangesAsync();
        return new ResourceResponse(entity.Id, entity.Title, entity.Description, entity.ResourceUrl, entity.ModuleCode, entity.CreatedAt);
    }

    public async Task<ResourceResponse?> UpdateAsync(int id, UpdateResourceRequest request)
    {
        var entity = await _db.AcademicResources.FindAsync(id); if (entity is null) return null;
        entity.Title = request.Title.Trim(); entity.Description = request.Description.Trim(); entity.ResourceUrl = request.ResourceUrl.Trim(); entity.ModuleCode = request.ModuleCode.Trim().ToUpperInvariant();
        await _db.SaveChangesAsync(); return await GetByIdAsync(id);
    }

    public async Task<bool> DeleteAsync(int id)
    {
        var entity = await _db.AcademicResources.FindAsync(id); if (entity is null) return false;
        _db.AcademicResources.Remove(entity); await _db.SaveChangesAsync(); return true;
    }
}
