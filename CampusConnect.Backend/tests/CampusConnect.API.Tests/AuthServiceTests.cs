using System;
using System.Collections.Generic;
using System.Threading.Tasks;

using CampusConnect.API.Data;
using CampusConnect.API.DTOs.Auth;
using CampusConnect.API.Helpers;
using CampusConnect.API.Services;

using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging.Abstractions;

using Xunit;

namespace CampusConnect.API.Tests;

public class AuthServiceTests
{
    private static AuthService CreateService(out CampusConnectDbContext db)
    {
        // Create a unique in-memory database for each test
        var options = new DbContextOptionsBuilder<CampusConnectDbContext>()
            .UseInMemoryDatabase(Guid.NewGuid().ToString())
            .Options;

        db = new CampusConnectDbContext(options);

        // Configuration required by JwtHelper
        var configValues = new Dictionary<string, string?>
        {
            ["Jwt:Key"] = "ThisIsADevelopmentOnlySecretKeyThatIsLongEnough123!",
            ["Jwt:Issuer"] = "CampusConnect.API",
            ["Jwt:Audience"] = "CampusConnect.Android",
            ["Jwt:ExpiryMinutes"] = "120"
        };

        var config = new ConfigurationBuilder()
            .AddInMemoryCollection(configValues)
            .Build();

        var jwtHelper = new JwtHelper(config);

        return new AuthService(
            db,
            jwtHelper,
            NullLogger<AuthService>.Instance
        );
    }

    [Fact]
    public async Task Register_CreatesUser()
    {
        // Arrange
        var service = CreateService(out var db);

        var request = new RegisterRequest
        {
            FullName = "Test User",
            Email = "test@example.com",
            Password = "Password123!"
        };

        // Act
        var result = await service.RegisterAsync(request);

        // Assert
        Assert.NotNull(result);
        Assert.Single(db.Users);
    }

    [Fact]
    public async Task Register_DuplicateEmail_ReturnsNull()
    {
        // Arrange
        var service = CreateService(out _);

        var request = new RegisterRequest
        {
            FullName = "Test User",
            Email = "test@example.com",
            Password = "Password123!"
        };

        // Act
        var firstResult = await service.RegisterAsync(request);
        var secondResult = await service.RegisterAsync(request);

        // Assert
        Assert.NotNull(firstResult);
        Assert.Null(secondResult);
    }

    [Fact]
    public async Task Login_WithCorrectPassword_ReturnsToken()
    {
        // Arrange
        var service = CreateService(out _);

        var registerRequest = new RegisterRequest
        {
            FullName = "Test User",
            Email = "test@example.com",
            Password = "Password123!"
        };

        await service.RegisterAsync(registerRequest);

        var loginRequest = new LoginRequest
        {
            Email = "test@example.com",
            Password = "Password123!"
        };

        // Act
        var result = await service.LoginAsync(loginRequest);

        // Assert
        Assert.NotNull(result);
        Assert.False(string.IsNullOrWhiteSpace(result!.Token));
    }
}