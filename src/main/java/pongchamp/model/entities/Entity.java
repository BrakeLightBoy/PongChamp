package pongchamp.model.entities;

import pongchamp.model.Collidable;
import pongchamp.model.math.Point;
import pongchamp.model.Metadata;

import java.util.UUID;

public abstract class Entity {

   private final UUID uuid;
   protected Point location;
   private Metadata metadata;

   public Entity(Point location) {
       this.location = location;
       this.uuid = UUID.randomUUID();
       this.metadata =  new Metadata();
   }

   public abstract void tick();

   public Point getLocation() {
       return location;
   }

   public void setLocation(Point location) {
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
