using System.ComponentModel.DataAnnotations;

namespace CampusConnect.API.DTOs.Events;

public class UpdateEventRequest
{
    [Required, StringLength(150)] public string Title { get; set; } = string.Empty;
    [Required, StringLength(1000)] public string Description { get; set; } = string.Empty;
    [Required, StringLength(200)] public string Location { get; set; } = string.Empty;
    [Required] public DateTime EventDate { get; set; }
}
