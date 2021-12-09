package pongchamp.pongchamp.model.entities;

import pongchamp.pongchamp.model.math.Point;
import pongchamp.pongchamp.model.Metadata;

import java.util.Objects;
import java.util.UUID;

public abstract class Entity {

   protected final UUID uuid;
   protected Point location;
   protected Metadata metadata;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return Objects.equals(uuid, entity.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
