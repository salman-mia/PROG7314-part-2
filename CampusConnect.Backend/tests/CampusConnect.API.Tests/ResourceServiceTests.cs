using System;
using System.Threading.Tasks;

using CampusConnect.API.Data;
using CampusConnect.API.DTOs.Resources;
using CampusConnect.API.Services;

using Microsoft.EntityFrameworkCore;

using Xunit;

namespace CampusConnect.API.Tests;

public class ResourceServiceTests
{
    [Fact]
    public async Task CreateResource_PersistsResource()
    {
        // Arrange - create a unique in-memory database
        var options = new DbContextOptionsBuilder<CampusConnectDbContext>()
            .UseInMemoryDatabase(Guid.NewGuid().ToString())
            .Options;

        await using var db = new CampusConnectDbContext(options);

        var service = new ResourceService(db);

        var request = new CreateResourceRequest
        {
            Title = "Notes",
            Description = "Week 1",
            ResourceUrl = "https://example.com/notes",
            ModuleCode = "prog7314"
        };

        // Act
        var result = await service.CreateAsync(request);

        // Assert
        Assert.NotNull(result);
        Assert.Equal("PROG7314", result.ModuleCode);
        Assert.Single(db.AcademicResources);
    }
}