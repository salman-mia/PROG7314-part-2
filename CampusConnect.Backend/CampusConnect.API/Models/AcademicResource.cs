namespace CampusConnect.API.Models;

public class AcademicResource
{
    public int Id { get; set; }
    public string Title { get; set; } = string.Empty;
    public string Description { get; set; } = string.Empty;
    public string ResourceUrl { get; set; } = string.Empty;
    public string ModuleCode { get; set; } = string.Empty;
    public DateTime CreatedAt { get; set; } = DateTime.UtcNow;
}
