package org.slytherin.cinema.dtos;
import org.slytherin.cinema.model.entities.Actor;
import org.slytherin.cinema.model.entities.Performance;

import java.util.List;

public class ActorDto {
    private long id;
    private String actorName;
    private String actorImg;

    private String roleName;

    public ActorDto() {
    }

    public ActorDto(Actor actor) {
        this.id = actor.getId();
        this.actorName = actor.getActorName();
        this.actorImg = actor.getActorImg();

    }

    public ActorDto(Performance performance) {
        this.id = performance.getId();
        this.roleName=performance.getRoleName();
        this.actorName = performance.getActor().getActorName();
        this.actorImg = performance.getActor().getActorImg();
    }

    public Actor fromDto(){
        return new Actor(this.id,this.actorName,this.actorImg);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getActorImg() {
        return actorImg;
    }

    public void setActorImg(String actorImg) {
        this.actorImg = actorImg;
    }



    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
