namespace CampusConnect.API.DTOs.LostAndFound;

public record LostAndFoundResponse(int Id, string Title, string Description, string Location, string Type, string Status, int PostedBy, DateTime CreatedAt);
