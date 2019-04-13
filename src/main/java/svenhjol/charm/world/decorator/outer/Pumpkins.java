package svenhjol.charm.world.decorator.outer;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.*;
import svenhjol.charm.Charm;
import svenhjol.charm.base.CharmLootTables;
import svenhjol.charm.crafting.feature.Barrel;
import svenhjol.charm.crafting.tile.TileBarrel;
import svenhjol.charm.world.feature.VillageDecorations;
import svenhjol.meson.decorator.MesonOuterDecorator;

import java.util.*;

public class Pumpkins extends MesonOuterDecorator
{
    public Pumpkins(World world, BlockPos pos, Random rand, List<ChunkPos> chunks)
    {
        super(world, pos, rand, chunks);
    }

    @Override
    public void generate()
    {
        int max = 3;
        for (int i = 0; i < max; i++) {
            if (rand.nextFloat() < VillageDecorations.pumpkinsWeight) continue;

            int xx = rand.nextInt(16) + 8;
            int zz = rand.nextInt(16) + 8;

            BlockPos current = world.getHeight(pos.add(xx, 0, zz));
            boolean airAbove = world.getBlockState(current) == Blocks.AIR.getDefaultState();
            boolean grassBelow = world.getBlockState(current.offset(EnumFacing.DOWN)) == Blocks.GRASS.getDefaultState();
            if (!airAbove || !grassBelow) continue;

            Block block;
            float r = rand.nextFloat();

            if (r <= 0.2) {
                block = Blocks.MELON_BLOCK;
            } else if (r <= 0.5) {
                block = Blocks.LIT_PUMPKIN;
            } else {
                block = Blocks.PUMPKIN;
            }

            world.setBlockState(current, block.getDefaultState());
        }
    }
}
