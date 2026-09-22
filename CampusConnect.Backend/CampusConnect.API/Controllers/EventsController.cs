using System.Security.Claims;
using CampusConnect.API.DTOs.Events;
using CampusConnect.API.Interfaces;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace CampusConnect.API.Controllers;

[ApiController]
[Route("api/[controller]")]
public class EventsController : ControllerBase
{
    private readonly IEventService _service;
    public EventsController(IEventService service) => _service = service;
    private int UserId => int.Parse(User.FindFirstValue(ClaimTypes.NameIdentifier)!);

    [AllowAnonymous, HttpGet]
    public async Task<IActionResult> GetAll() => Ok(await _service.GetAllAsync());

    [AllowAnonymous, HttpGet("{id:int}")]
    public async Task<IActionResult> GetById(int id) => (await _service.GetByIdAsync(id)) is { } item ? Ok(item) : NotFound();

    [Authorize, HttpPost]
    public async Task<IActionResult> Create(CreateEventRequest request)
    {
        var created = await _service.CreateAsync(request, UserId);
        return CreatedAtAction(nameof(GetById), new { id = created.Id }, created);
    }

    [Authorize, HttpPut("{id:int}")]
    public async Task<IActionResult> Update(int id, UpdateEventRequest request) => (await _service.UpdateAsync(id, request, UserId)) is { } item ? Ok(item) : NotFound(new { message = "Event not found or you are not the owner." });

    [Authorize, HttpDelete("{id:int}")]
    public async Task<IActionResult> Delete(int id) => await _service.DeleteAsync(id, UserId) ? NoContent() : NotFound(new { message = "Event not found or you are not the owner." });

    [Authorize, HttpPost("{id:int}/rsvp")]
    public async Task<IActionResult> Rsvp(int id) => await _service.RsvpAsync(id, UserId) ? Ok(new { message = "RSVP successful." }) : Conflict(new { message = "Event does not exist or you already RSVP'd." });

    [Authorize, HttpDelete("{id:int}/rsvp")]
    public async Task<IActionResult> CancelRsvp(int id) => await _service.CancelRsvpAsync(id, UserId) ? Ok(new { message = "RSVP cancelled." }) : NotFound(new { message = "RSVP not found." });
}
