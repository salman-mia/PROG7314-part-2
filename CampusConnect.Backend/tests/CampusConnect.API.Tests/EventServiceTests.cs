using System;
using System.Threading.Tasks;

using CampusConnect.API.Data;
using CampusConnect.API.DTOs.Events;
using CampusConnect.API.Models;
using CampusConnect.API.Services;

using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging.Abstractions;

using Xunit;

namespace CampusConnect.API.Tests;

public class EventServiceTests
{
    private static EventService CreateService(out CampusConnectDbContext db)
    {
        var options = new DbContextOptionsBuilder<CampusConnectDbContext>()
            .UseInMemoryDatabase(Guid.NewGuid().ToString())
            .Options;

        db = new CampusConnectDbContext(options);

        return new EventService(
            db,
            NullLogger<EventService>.Instance
        );
    }

    [Fact]
    public async Task CreateEvent_PersistsEvent()
    {
        // Arrange
        var service = CreateService(out var db);

        db.Users.Add(new User
        {
            Id = 1,
            FullName = "User",
            Email = "u@test.com",
            PasswordHash = "x"
        });

        await db.SaveChangesAsync();

        var request = new CreateEventRequest
        {
            Title = "Study Session",
            Description = "Math",
            Location = "Library",
            EventDate = DateTime.UtcNow.AddDays(1)
        };

        // Act
        var result = await service.CreateAsync(request, 1);

        // Assert
        Assert.Equal("Study Session", result.Title);
        Assert.Single(db.Events);
    }

    [Fact]
    public async Task Rsvp_CannotBeDuplicated()
    {
        // Arrange
        var service = CreateService(out var db);

        db.Users.Add(new User
        {
            Id = 1,
            FullName = "User",
            Email = "u@test.com",
            PasswordHash = "x"
        });

        db.Events.Add(new Event
        {
            Id = 1,
            Title = "Event",
            Description = "D",
            Location = "L",
            EventDate = DateTime.UtcNow.AddDays(1),
            CreatedBy = 1
        });

        await db.SaveChangesAsync();

        // Act
        var firstRsvp = await service.RsvpAsync(1, 1);
        var duplicateRsvp = await service.RsvpAsync(1, 1);

        // Assert
        Assert.True(firstRsvp);
        Assert.False(duplicateRsvp);
    }
}