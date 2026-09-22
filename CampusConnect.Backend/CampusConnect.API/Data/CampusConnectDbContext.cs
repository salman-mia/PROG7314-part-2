using CampusConnect.API.Models;
using Microsoft.EntityFrameworkCore;

namespace CampusConnect.API.Data;

public class CampusConnectDbContext : DbContext
{
    public CampusConnectDbContext(DbContextOptions<CampusConnectDbContext> options) : base(options) { }

    public DbSet<User> Users => Set<User>();
    public DbSet<Event> Events => Set<Event>();
    public DbSet<EventRSVP> EventRSVPs => Set<EventRSVP>();
    public DbSet<AcademicResource> AcademicResources => Set<AcademicResource>();
    public DbSet<LostAndFoundItem> LostAndFoundItems => Set<LostAndFoundItem>();

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<User>().HasIndex(u => u.Email).IsUnique();

        modelBuilder.Entity<Event>()
            .HasOne(e => e.Creator)
            .WithMany(u => u.CreatedEvents)
            .HasForeignKey(e => e.CreatedBy)
            .OnDelete(DeleteBehavior.Restrict);

        modelBuilder.Entity<EventRSVP>()
            .HasIndex(r => new { r.EventId, r.UserId })
            .IsUnique();

        modelBuilder.Entity<EventRSVP>()
            .HasOne(r => r.Event)
            .WithMany(e => e.RSVPs)
            .HasForeignKey(r => r.EventId)
            .OnDelete(DeleteBehavior.Cascade);

        modelBuilder.Entity<EventRSVP>()
            .HasOne(r => r.User)
            .WithMany(u => u.EventRSVPs)
            .HasForeignKey(r => r.UserId)
            .OnDelete(DeleteBehavior.Restrict);

        modelBuilder.Entity<LostAndFoundItem>()
            .HasOne(i => i.User)
            .WithMany(u => u.LostAndFoundItems)
            .HasForeignKey(i => i.PostedBy)
            .OnDelete(DeleteBehavior.Restrict);
    }
}
