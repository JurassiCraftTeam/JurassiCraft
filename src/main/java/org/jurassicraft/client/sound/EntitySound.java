package org.jurassicraft.client.sound;

import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import java.util.function.Predicate;
import org.jurassicraft.client.event.ClientEventHandler;
import org.jurassicraft.client.proxy.ClientProxy;

public class EntitySound<T extends Entity> extends MovingSound {

    protected final T entity;
    protected final Predicate<T> predicate;

    public EntitySound(T entity, SoundEvent soundEvent, SoundCategory soundCategory, Predicate<T> predicate) {
        super(soundEvent, soundCategory);
        this.entity = entity;
        this.predicate = predicate;
    }

    public EntitySound(T entity, SoundEvent soundEvent, SoundCategory soundCategory) {
        this(entity, soundEvent, soundCategory, t -> true);
    }

    @Override
    public void update() {
        if (this.entity.isDead || !predicate.test(this.entity)) {
            this.donePlaying = true;
        } else {
        	 EntityPlayer player = ClientProxy.MC.player;
             this.xPosF = (float) (entity.posX + (player.posX - entity.posX) / 2);
             this.yPosF = (float) (entity.posY + (player.posY - entity.posY) / 2);
             this.zPosF = (float) (entity.posZ + (player.posZ - entity.posZ) / 2);
        }
    }

    public void setFinished() {
        this.donePlaying = true;
    }


}
