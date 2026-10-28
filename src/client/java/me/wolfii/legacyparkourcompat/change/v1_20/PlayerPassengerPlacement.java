package me.wolfii.legacyparkourcompat.change.v1_20;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.PlayerPassengerPlacementHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.PlayerPassengerPlacementHook.Carrier;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.PlayerPassengerPlacementHook.Input;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.PlayerPassengerPlacementHook.Invocation;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.PlayerPassengerPlacementHook.Placement;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.animal.camel.Camel;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.Strider;
import net.minecraft.world.phys.EntityDimensions;

import java.util.Optional;
import java.util.Set;

/**
 * Restores the complete 1.20.1 local-player passenger placement operation.
 *
 * <p>Source references: Minecraft 1.20.1 Mojmap
 * {@code net.minecraft.world.entity.Entity.positionRider(Entity, Entity.MoveFunction)},
 * {@code Entity.getPassengersRidingOffset()}, {@code Player.getMyRidingOffset()},
 * {@code EntityType} registrations, {@code ChestBoat.getSinglePassengerXOffset()}, and
 * {@code Camel.getBodyAnchorAnimationYOffset(boolean, float)};
 * Minecraft 1.20.2 Mojmap {@code Entity.positionRider(Entity, Entity.MoveFunction)},
 * {@code Entity.getPassengerRidingPosition(Entity)},
 * {@code LivingEntity.getMyRidingOffset(Entity)}, and the listed vehicle
 * {@code getPassengerAttachmentPoint(Entity, EntityDimensions, float)} overrides. The ChestBoat
 * single-passenger offset is {@code 0.15F} in both source releases.
 *
 * <p>The 1.20.1 and 1.20.2 Mojmap sources differ in the common writer, Player's riding offset, and
 * the vehicle attachment writers. The accepted 1.20 to 1.20.1 movement-source alias audit found
 * the scoped movement sources byte-identical, so this change covers that profile group only;
 * unsupported or modern-only entity types retain the native callback. A V1_19_4 native provider
 * bounds older profiles without claiming historical 1.19.4 placement behavior.
 */
@MovementChange(emulates = ParkourVersion.V1_20)
public final class PlayerPassengerPlacement implements PlayerPassengerPlacementHook {
    private static final Set<String> SOURCE_1_20_1_ENTITY_TYPES = Set.of(
        "allay", "area_effect_cloud", "armor_stand", "arrow", "axolotl", "bat", "bee", "blaze",
        "block_display", "boat", "camel", "cat", "cave_spider", "chest_boat", "chest_minecart",
        "chicken", "cod", "command_block_minecart", "cow", "creeper", "dolphin", "donkey",
        "dragon_fireball", "drowned", "egg", "elder_guardian", "end_crystal", "ender_dragon",
        "ender_pearl", "enderman", "endermite", "evoker", "evoker_fangs", "experience_bottle",
        "experience_orb", "eye_of_ender", "falling_block", "fireball", "firework_rocket",
        "fishing_bobber", "fox", "frog", "furnace_minecart", "ghast", "giant", "glow_item_frame",
        "glow_squid", "goat", "guardian", "hoglin", "hopper_minecart", "horse", "husk",
        "illusioner", "interaction", "iron_golem", "item", "item_display", "item_frame", "leash_knot",
        "lightning_bolt", "llama", "llama_spit", "magma_cube", "marker", "minecart", "mooshroom",
        "mule", "ocelot", "painting", "panda", "parrot", "phantom", "pig", "piglin", "piglin_brute",
        "pillager", "player", "polar_bear", "potion", "pufferfish", "rabbit", "ravager", "salmon",
        "sheep", "shulker", "shulker_bullet", "silverfish", "skeleton", "skeleton_horse", "slime",
        "small_fireball", "sniffer", "snow_golem", "snowball", "spawner_minecart", "spectral_arrow",
        "spider", "squid", "stray", "strider", "tadpole", "text_display", "tnt", "tnt_minecart",
        "trader_llama", "trident", "tropical_fish", "turtle", "vex", "villager", "vindicator",
        "wandering_trader", "warden", "witch", "wither", "wither_skeleton", "wither_skull", "wolf",
        "zoglin", "zombie", "zombie_horse", "zombie_villager", "zombified_piglin"
    );

    @Override
    public Optional<Placement> placement(LocalPlayer passenger, Entity vehicle) {
        if (passenger == null || vehicle == null || !(vehicle.level().isClientSide())) {
            return Optional.empty();
        }

        var id = BuiltInRegistries.ENTITY_TYPE.getKey(vehicle.getType());
        if (!"minecraft".equals(id.getNamespace())) {
            return Optional.empty();
        }
        String type = id.getPath();
        if (!isSourceEraType(type)) {
            return Optional.empty();
        }

        // 1.20.1 has no generic entity-scale attribute. Preserve a modern non-default scale as
        // native behavior instead of feeding its dimensions into the historical formula.
        if (vehicle instanceof LivingEntity living && living.getScale() != 1.0F) {
            return Optional.empty();
        }

        Carrier carrier = carrier(type);
        if (carrier == Carrier.UNKNOWN) {
            return Optional.empty();
        }

        int passengerIndex = vehicle.getPassengers().indexOf(passenger);
        int passengerCount = vehicle.getPassengers().size();
        float bodyYaw = vehicle instanceof LivingEntity living ? living.getYBodyRot() : vehicle.getYRot();
        double camelAnchor = vehicle instanceof Camel camel ? camelBodyAnchor(camel, passengerIndex == 0) : 0.0;
        double chickenY = "chicken".equals(type) ? vehicle.getY(0.5) : 0.0;
        float standAnimation = vehicle instanceof AbstractHorse horse ? horse.getStandAnim(0.0F) : 0.0F;
        float walkPosition = vehicle instanceof Strider strider ? strider.walkAnimation.position() : 0.0F;
        float walkSpeed = vehicle instanceof Strider strider ? strider.walkAnimation.speed() : 0.0F;
        float dragonBodyHeight = vehicle instanceof EnderDragon dragon
            ? dragon.getSubEntities()[0].getBbHeight()
            : 0.0F;

        EntityDimensions dimensions = vehicle.getDimensions(vehicle.getPose());
        Input input = new Input(
            carrier,
            Invocation.CLIENT_RIDE_TICK,
            vehicle.hasPassenger(passenger),
            passengerIndex,
            passengerCount,
            vehicle.isRemoved(),
            vehicle instanceof AgeableMob ageable && ageable.isBaby(),
            type.contains("bamboo"),
            vehicle.getX(),
            vehicle.getY(),
            vehicle.getZ(),
            dimensions.height(),
            vehicle.getBbHeight(),
            vehicle instanceof Camel camel ? legacyCamelPoseHeight(camel) : 0.0F,
            vehicle.getEyeHeight(),
            dragonBodyHeight,
            walkPosition,
            walkSpeed,
            camelAnchor,
            chickenY,
            isChestBoat(type) ? 0.15F : 0.0F,
            vehicle.getYRot(),
            bodyYaw,
            standAnimation
        );
        return LegacyPlayerPassengerPlacement.calculate(passenger, vehicle, input);
    }

    private static boolean isSourceEraType(String type) {
        if (SOURCE_1_20_1_ENTITY_TYPES.contains(type)) {
            return true;
        }
        // The 1.20.1 source registers boat variants through a factory rather than literal strings.
        return type.matches("(oak|spruce|birch|jungle|acacia|dark_oak|mangrove)_(chest_)?boat")
            || type.equals("bamboo_raft")
            || type.equals("bamboo_chest_raft");
    }

    private static boolean isChestBoat(String type) {
        return type.equals("chest_boat") || type.endsWith("_chest_boat") || type.equals("bamboo_chest_raft");
    }

    private static Carrier carrier(String type) {
        if (type.matches("(oak|spruce|birch|jungle|acacia|dark_oak|mangrove)_(chest_)?boat")
            || type.equals("boat") || type.equals("chest_boat")
            || type.equals("bamboo_raft") || type.equals("bamboo_chest_raft")) {
            return Carrier.BOAT;
        }
        if (type.endsWith("minecart")) {
            return Carrier.ABSTRACT_MINECART;
        }
        return switch (type) {
            case "camel" -> Carrier.CAMEL;
            case "chicken" -> Carrier.CHICKEN;
            case "cat" -> Carrier.CAT;
            case "cow", "mooshroom" -> Carrier.COW;
            case "fox" -> Carrier.FOX;
            case "frog" -> Carrier.FROG;
            case "goat" -> Carrier.GOAT;
            case "horse", "zombie_horse" -> Carrier.ABSTRACT_HORSE;
            case "donkey", "mule" -> Carrier.CHESTED_HORSE;
            case "llama", "trader_llama" -> Carrier.LLAMA;
            case "skeleton_horse" -> Carrier.SKELETON_HORSE;
            case "ocelot" -> Carrier.OCELOT;
            case "panda" -> Carrier.PANDA;
            case "parrot" -> Carrier.PARROT;
            case "pig" -> Carrier.PIG;
            case "sheep" -> Carrier.SHEEP;
            case "sniffer" -> Carrier.SNIFFER;
            case "turtle" -> Carrier.TURTLE;
            case "wolf" -> Carrier.WOLF;
            case "ender_dragon" -> Carrier.ENDER_DRAGON;
            case "evoker", "illusioner", "pillager", "vindicator" -> Carrier.ABSTRACT_ILLAGER;
            case "cave_spider" -> Carrier.SPIDER;
            case "elder_guardian" -> Carrier.ELDER_GUARDIAN;
            case "enderman" -> Carrier.ENDERMAN;
            case "endermite" -> Carrier.ENDERMITE;
            case "ghast" -> Carrier.GHAST;
            case "guardian" -> Carrier.GUARDIAN;
            case "hoglin" -> Carrier.HOGLIN;
            case "husk" -> Carrier.HUSK;
            case "phantom" -> Carrier.PHANTOM;
            case "piglin_brute" -> Carrier.ABSTRACT_PIGLIN;
            case "piglin" -> Carrier.PIGLIN;
            case "ravager" -> Carrier.RAVAGER;
            case "silverfish" -> Carrier.SILVERFISH;
            case "slime", "magma_cube" -> Carrier.SLIME;
            case "spider" -> Carrier.SPIDER;
            case "strider" -> Carrier.STRIDER;
            case "vex" -> Carrier.VEX;
            case "warden" -> Carrier.WARDEN;
            case "witch" -> Carrier.WITCH;
            case "zoglin" -> Carrier.ZOGLIN;
            case "zombie" -> Carrier.ZOMBIE;
            case "zombie_villager" -> Carrier.ZOMBIE_VILLAGER;
            case "zombified_piglin" -> Carrier.ZOMBIFIED_PIGLIN;
            default -> Carrier.OTHER_BASE;
        };
    }

    private static float legacyCamelPoseHeight(Camel camel) {
        float legacyScale = camel.isBaby() ? 0.5F : 1.0F;
        Pose pose = camel.isCamelSitting() ? Pose.SITTING : Pose.STANDING;
        float height = pose == Pose.SITTING ? 2.375F - 1.43F : 2.375F;
        return height * legacyScale;
    }

    private static double camelBodyAnchor(Camel camel, boolean frontSeat) {
        float legacyScale = camel.isBaby() ? 0.5F : 1.0F;
        double offset = legacyCamelPoseHeight(camel) - (camel.isBaby() ? 0.35F : 0.6F);
        float sittingHeight = legacyScale * 1.43F;
        float verticalDrop = sittingHeight - legacyScale * 0.2F;
        float bottomPoint = sittingHeight - verticalDrop;
        boolean transition = camel.isInPoseTransition();
        boolean sitting = camel.isCamelSitting();

        if (transition) {
            int duration = sitting ? 40 : 52;
            int halfPoint;
            float flexPointOffset;
            if (sitting) {
                halfPoint = 28;
                flexPointOffset = frontSeat ? 0.5F : 0.1F;
            } else {
                halfPoint = frontSeat ? 24 : 32;
                flexPointOffset = frontSeat ? 0.6F : 0.35F;
            }

            float poseTime = Mth.clamp((float)camel.getPoseTime(), 0.0F, duration);
            boolean firstPart = poseTime < halfPoint;
            float part = firstPart ? poseTime / halfPoint : (poseTime - halfPoint) / (duration - halfPoint);
            float flexPoint = sittingHeight - flexPointOffset * verticalDrop;
            offset += sitting
                ? Mth.lerp(part, firstPart ? sittingHeight : flexPoint, firstPart ? flexPoint : bottomPoint)
                : Mth.lerp(part, firstPart ? bottomPoint - sittingHeight : bottomPoint - flexPoint,
                    firstPart ? bottomPoint - flexPoint : 0.0F);
        }

        if (sitting && !transition) {
            offset += bottomPoint;
        }
        return offset;
    }
}
