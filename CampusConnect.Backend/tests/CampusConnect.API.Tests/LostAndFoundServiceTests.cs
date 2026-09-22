using System;
using System.Threading.Tasks;

using CampusConnect.API.Data;
using CampusConnect.API.DTOs.LostAndFound;
using CampusConnect.API.Models;
using CampusConnect.API.Services;

using Microsoft.EntityFrameworkCore;

using Xunit;

namespace CampusConnect.API.Tests;

public class LostAndFoundServiceTests
{
    [Fact]
    public async Task CreateItem_DefaultsToOpen()
    {
        // Arrange - create a unique in-memory database
        var options = new DbContextOptionsBuilder<CampusConnectDbContext>()
            .UseInMemoryDatabase(Guid.NewGuid().ToString())
            .Options;

        await using var db = new CampusConnectDbContext(options);

        // Add a test user
        db.Users.Add(new User
        {
            Id = 1,
            FullName = "User",
            Email = "u@test.com",
            PasswordHash = "x"
        });

        await db.SaveChangesAsync();

        var service = new LostAndFoundService(db);

        var request = new CreateLostAndFoundRequest
        {
            Title = "Wallet",
            Description = "Black wallet",
            Location = "Library",
            Type = "Lost"
        };

        // Act
        var result = await service.CreateAsync(request, 1);

        // Assert
        Assert.NotNull(result);
        Assert.Equal("Open", result.Status);
    }
}