namespace CampusConnect.API.DTOs.Auth;

public record AuthResponse(int UserId, string FullName, string Email, string Token);
