package svenhjol.charm.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import svenhjol.charm.blockentity.BookcaseBlockEntity;
import svenhjol.meson.MesonModule;
import svenhjol.meson.block.MesonBlockEntity;
import svenhjol.meson.enums.IStorageMaterial;

import javax.annotation.Nullable;

public class BookcaseBlock extends MesonBlockEntity {
    public static final IntProperty SLOTS = IntProperty.of("slots", 0, BookcaseBlockEntity.SIZE);

    protected MesonModule module;
    protected IStorageMaterial type;

    public BookcaseBlock(MesonModule module, IStorageMaterial type) {
        super(module, type.asString() + "_bookcase", AbstractBlock.Settings
            .copy(Blocks.BOOKSHELF));

        this.module = module;
        this.type = type;

        setDefaultState(getDefaultState().with(SLOTS, 0));
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient && !player.isSpectator()) {

            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof BookcaseBlockEntity) {
                BookcaseBlockEntity bookcase = (BookcaseBlockEntity)blockEntity;
                bookcase.checkLootInteraction(player);
                player.openHandledScreen(bookcase);
            }
        }
        return ActionResult.SUCCESS;
    }


    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tile = world.getBlockEntity(pos);
            if (tile instanceof BookcaseBlockEntity) {
                ItemScatterer.spawn(world, pos, (Inventory) tile);
                world.updateComparators(pos, this);
            }
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (stack.hasCustomName()) {
            BlockEntity tile = world.getBlockEntity(pos);
            if (tile instanceof BookcaseBlockEntity)
                ((BookcaseBlockEntity) tile).setCustomName(stack.getName());
        }
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.NORMAL;
    }

    @Override
    public ItemGroup getItemGroup() {
        return ItemGroup.DECORATIONS;
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return state.get(SLOTS);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        BookcaseBlockEntity bookcase = new BookcaseBlockEntity();
        bookcase.setCustomName(new TranslatableText("block." + this.module.mod.getId() + "." + type.asString() + "_bookcase"));
        return bookcase;
    }

    @Override
    public int getMaxStackSize() {
        return 64;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(SLOTS);
    }
}
