package net.theblindbandit6.darkcompass.item.custom;

import net.minecraft.block.BlockState;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Vanishable;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.theblindbandit6.darkcompass.item.ModItems;
import net.theblindbandit6.darkcompass.sound.ModSoundEvents;
import org.jetbrains.annotations.Nullable;

public class DarkCompassItem
        extends Item
        implements Vanishable {
    //Radius checked by the compass
    public static final int RADIUS = 50;
    public static final String DARKPOS_KEY = "DarkPos";

    public DarkCompassItem(Settings settings) {
        super(settings);
    }

    //On use, it grabs the nearest dark block and saves the coordinates of that block to the nbt data of the compass which
    //is then used by the compass to point towards those coordinates.
    //Current issue is that the compass loses it's nbt data when moved around the inventory or removed from the inventory.
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        BlockPos center = user.getBlockPos();
        if(hand == Hand.MAIN_HAND){
            world.playSoundFromEntity(null, user, ModSoundEvents.ITEM_DARK_COMPASS_LOCK, SoundCategory.PLAYERS, 1.0f, 0.7f);
        }

        if (hand == Hand.MAIN_HAND) {
            BlockPos darkPos = getClosestAirBlock(world, center);

            if (darkPos != null) {
                ItemStack itemStack = user.getMainHandStack();
                ItemStack itemStack2 = new ItemStack(ModItems.DARK_COMPASS, 1);
                NbtCompound nbtCompound = itemStack.hasNbt() ? itemStack.getNbt().copy() : new NbtCompound();
                itemStack2.setNbt(nbtCompound);
                itemStack.decrement(1);

                this.writeNbt(darkPos, nbtCompound);

                if (!user.getInventory().insertStack(itemStack2)) {
                    user.dropItem(itemStack2, false);
                }
                return super.use(world, user, hand);
            } else {
                ItemStack itemStack = user.getMainHandStack();
                ItemStack itemStack2 = new ItemStack(ModItems.DARK_COMPASS, 1);
                NbtCompound nbtCompound = itemStack.hasNbt() ? itemStack.getNbt().copy() : new NbtCompound();
                itemStack2.setNbt(nbtCompound);
                itemStack.decrement(1);

                this.clearNBT(nbtCompound);

                if (!user.getInventory().insertStack(itemStack2)) {
                    user.dropItem(itemStack2, false);
                }
                return super.use(world, user, hand);
            }
        }
        return super.use(world, user, hand);
    }

    //Writes the value of DARKPOS_KEY to the nbt data of the item
    private void writeNbt(BlockPos pos, NbtCompound nbt) {
        nbt.put(DARKPOS_KEY, NbtHelper.fromBlockPos(pos));
    }
    //Clears nbt data of the item
    private void clearNBT(NbtCompound nbt) {
        nbt.remove(DARKPOS_KEY);
    }

    //Works out the closest Air Block that has a sky and block light of 0 and outputs the BlockPos of that Air Block
    public static BlockPos getClosestAirBlock(World world, BlockPos center) {
        if (world instanceof ServerWorld) {
            return getClosestAirBlock((ServerWorld) world, center);
        }
        int lowestBlock = world.getBottomY();
        double closestDistance = Double.MAX_VALUE;
        BlockPos closestAirBlock = null;
        for (int x = -RADIUS; x <= RADIUS; x++) {
            for (int y = -RADIUS; y <= RADIUS; y++) {
                for (int z = -RADIUS; z <= RADIUS; z++) {
                    BlockPos pos = center.add(x, y, z);
                    if (pos.getY() > lowestBlock) {
                        BlockState state = world.getBlockState(pos);
                        BlockState stateUp = world.getBlockState(pos.up());
                        if (state.isSolidBlock(world, pos) && stateUp.isAir() && stateUp.getLuminance() == 0 && world.getLightLevel(LightType.BLOCK, pos.up()) == 0 && world.getLightLevel(LightType.SKY, pos.up()) == 0) {
                            double distance = pos.getSquaredDistance(center);
                            if (distance < closestDistance) {
                                closestDistance = distance;
                                closestAirBlock = pos;
                            }
                        }
                    }
                }
            }
        }
        return closestAirBlock;
    }

    //Server World version of the above method
    public static BlockPos getClosestAirBlock(ServerWorld world, BlockPos center) {
        int lowestBlock = world.getBottomY();
        double closestDistance = Double.MAX_VALUE;
        BlockPos closestAirBlock = null;
        for (int x = -RADIUS; x <= RADIUS; x++) {
            for (int y = -RADIUS; y <= RADIUS; y++) {
                for (int z = -RADIUS; z <= RADIUS; z++) {
                    BlockPos pos = center.add(x, y, z);
                    if (pos.getY() > lowestBlock) {
                        BlockState state = world.getBlockState(pos);
                        BlockState stateUp = world.getBlockState(pos.up());
                        if (state.isSolidBlock(world, pos) && stateUp.isAir() && stateUp.getLuminance() == 0 && world.getLightLevel(LightType.BLOCK, pos.up()) == 0 && world.getLightLevel(LightType.SKY, pos.up()) == 0) {
                            double distance = pos.getSquaredDistance(center);
                            if (distance < closestDistance) {
                                closestDistance = distance;
                                closestAirBlock = pos;
                            }
                        }
                    }
                }
            }
        }
        return closestAirBlock;
    }

    //Creates the global position for the compass
    @Nullable
    public static GlobalPos createDarkPos(ClientWorld world, NbtCompound nbt) {
        boolean bl = nbt.contains(DARKPOS_KEY);
        if(bl){
            BlockPos blockPos = NbtHelper.toBlockPos(nbt.getCompound(DARKPOS_KEY));
            return world.getDimension().natural() ? GlobalPos.create(world.getRegistryKey(), blockPos) : null;
        } else {
            return null;
        }
    }
}
