package svenhjol.charm.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BarrelTileEntity;
import net.minecraft.block.entity.TileEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.TranslationTextComponent;
import net.minecraft.util.collection.NonNullList;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IBlockReader;
import svenhjol.charm.module.VariantBarrels;
import svenhjol.charm.base.CharmModule;
import svenhjol.charm.base.block.ICharmBlock;
import svenhjol.charm.base.enums.IVariantMaterial;
import svenhjol.charm.mixin.accessor.BarrelTileEntityAccessor;

import javax.annotation.Nullable;

public class VariantBarrelBlock extends BarrelBlock implements ICharmBlock {
    protected CharmModule module;
    protected IVariantMaterial type;

    public VariantBarrelBlock(CharmModule module, IVariantMaterial type) {
        super(AbstractBlock.Properties.copy(Blocks.BARREL));

        this.module = module;
        this.type = type;

        this.register(module, type.asString() + "_barrel");
        this.setDefaultState(this.getStateManager()
            .getDefaultState()
            .with(FACING, Direction.NORTH)
            .with(OPEN, false)
        );
    }

    @Override
    public ItemGroup getItemGroup() {
        return ItemGroup.DECORATIONS;
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> list) {
        if (enabled())
            super.fillItemGroup(group, list);
    }

    @Override
    public boolean enabled() {
        return module.enabled;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        BarrelTileEntity barrel = BarrelTileEntityAccessor.invokeConstructor(VariantBarrels.BLOCK_ENTITY);
        barrel.setCustomName(new TranslationTextComponent("block." + module.mod + "." + type.asString() + "_barrel"));
        return barrel;
    }
}
