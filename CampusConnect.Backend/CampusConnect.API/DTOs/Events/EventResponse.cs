namespace CampusConnect.API.DTOs.Events;

public record EventResponse(int Id, string Title, string Description, string Location, DateTime EventDate, int CreatedBy, DateTime CreatedAt, int RSVPCount);
