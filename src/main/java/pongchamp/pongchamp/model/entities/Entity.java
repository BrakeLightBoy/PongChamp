package pongchamp.pongchamp.model.entities;

import pongchamp.pongchamp.model.math.Point;

import java.util.Objects;
import java.util.UUID;

public abstract class Entity {

   private final UUID uuid;
   private Point location;

    public Entity(Point location) {
       this.location = location;
       this.uuid = UUID.randomUUID();
    }

    public abstract void tick();

    public Point getLocation() {
       return location;
   }

    public void setLocation(Point location) {
       this.location = location;
   }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (!(obj instanceof Entity)){
            return false;
        }
        Entity entity = (Entity) obj;
        return Objects.equals(uuid, entity.uuid);
    }
}
