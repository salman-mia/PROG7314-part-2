namespace CampusConnect.API.Models;

public class User
{
    public int Id { get; set; }
    public string FullName { get; set; } = string.Empty;
    public string Email { get; set; } = string.Empty;
    public string PasswordHash { get; set; } = string.Empty;
    public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
    public string PreferredTheme { get; set; } = "system";
    public bool NotificationsEnabled { get; set; } = true;

    public ICollection<Event> CreatedEvents { get; set; } = new List<Event>();
    public ICollection<EventRSVP> EventRSVPs { get; set; } = new List<EventRSVP>();
    public ICollection<LostAndFoundItem> LostAndFoundItems { get; set; } = new List<LostAndFoundItem>();
}
