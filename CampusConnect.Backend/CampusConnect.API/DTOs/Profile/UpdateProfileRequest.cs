using System.ComponentModel.DataAnnotations;

namespace CampusConnect.API.DTOs.Profile;

public class UpdateProfileRequest
{
    [Required, StringLength(100, MinimumLength = 2)] public string FullName { get; set; } = string.Empty;
    [Required, RegularExpression("system|light|dark")] public string PreferredTheme { get; set; } = "system";
    public bool NotificationsEnabled { get; set; } = true;
}
