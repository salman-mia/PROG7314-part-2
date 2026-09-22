namespace CampusConnect.API.DTOs.Profile;

public record ProfileResponse(int Id, string FullName, string Email, string PreferredTheme, bool NotificationsEnabled);
