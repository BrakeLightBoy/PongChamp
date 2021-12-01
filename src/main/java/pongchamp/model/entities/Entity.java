package pongchamp.model.entities;

import pongchamp.model.Board;
import pongchamp.model.math.Point;
import pongchamp.model.Metadata;

import java.util.UUID;

public abstract class Entity {

   protected final UUID uuid;
   protected Point location;
   protected Metadata metadata;
   protected Board board;

   public Entity(Board board,Point location) {
       this.location = location;
       this.uuid = UUID.randomUUID();
       this.metadata =  new Metadata();
       this.board = board;
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

    public Board getBoard() {
        return board;
    }
}
