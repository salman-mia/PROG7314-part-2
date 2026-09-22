namespace CampusConnect.API.Models;

public class EventRSVP
{
    public int Id { get; set; }
    public int EventId { get; set; }
    public int UserId { get; set; }
    public DateTime RSVPDate { get; set; } = DateTime.UtcNow;

    public Event? Event { get; set; }
    public User? User { get; set; }
}
