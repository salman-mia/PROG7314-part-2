using CampusConnect.API.Data;
using CampusConnect.API.DTOs.Auth;
using CampusConnect.API.Helpers;
using CampusConnect.API.Interfaces;
using CampusConnect.API.Models;
using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;

namespace CampusConnect.API.Services;

public class AuthService : IAuthService
{
    private readonly CampusConnectDbContext _db;
    private readonly PasswordHasher<User> _passwordHasher = new();
    private readonly JwtHelper _jwtHelper;
    private readonly ILogger<AuthService> _logger;

    public AuthService(CampusConnectDbContext db, JwtHelper jwtHelper, ILogger<AuthService> logger)
    {
        _db = db; _jwtHelper = jwtHelper; _logger = logger;
    }

    public async Task<AuthResponse?> RegisterAsync(RegisterRequest request)
    {
        var email = request.Email.Trim().ToLowerInvariant();
        if (await _db.Users.AnyAsync(u => u.Email == email)) return null;

        var user = new User { FullName = request.FullName.Trim(), Email = email };
        user.PasswordHash = _passwordHasher.HashPassword(user, request.Password);
        _db.Users.Add(user);
        await _db.SaveChangesAsync();
        _logger.LogInformation("User registered: {UserId}", user.Id);
        return new AuthResponse(user.Id, user.FullName, user.Email, _jwtHelper.GenerateToken(user));
    }

    public async Task<AuthResponse?> LoginAsync(LoginRequest request)
    {
        var email = request.Email.Trim().ToLowerInvariant();
        var user = await _db.Users.SingleOrDefaultAsync(u => u.Email == email);
        if (user is null) return null;

        var result = _passwordHasher.VerifyHashedPassword(user, user.PasswordHash, request.Password);
        if (result == PasswordVerificationResult.Failed) return null;

        _logger.LogInformation("User logged in: {UserId}", user.Id);
        return new AuthResponse(user.Id, user.FullName, user.Email, _jwtHelper.GenerateToken(user));
    }
}
