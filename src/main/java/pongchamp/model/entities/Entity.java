package pongchamp.model.entities;

import pongchamp.model.Collidable;
import pongchamp.model.math.Location;
import pongchamp.model.Metadata;

import java.util.UUID;

public abstract class Entity implements Collidable {

   private final UUID uuid;
   protected Location location;
   private Metadata metadata;

   public Entity(Location location) {
       this.location = location;
       this.uuid = UUID.randomUUID();
       this.metadata =  new Metadata();
   }

   public abstract void tick();

   public Location getLocation() {
       return location;
   }

   public void setLocation(Location location) {
       this.location = location;
   }

   public UUID getUuid() {
       return uuid;
   }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }
}
