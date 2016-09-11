package com.trials.modsquad.block.machine;

import com.trials.modsquad.Ref;
import com.trials.modsquad.block.tile.TileToaster;
import com.trials.modsquad.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.datafix.fixes.PaintingDirection;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockToaster extends Block {

    public static final AxisAlignedBB[] bounds = {
            new AxisAlignedBB(4 * 0.0625, 0 * 0.0625, 1 * 0.0625, 12 * 0.0625, 11 * 0.0625, 15 * 0.0625),
            new AxisAlignedBB(1 * 0.0625, 0 * 0.0625, 4 * 0.0625, 15 * 0.0625, 11 * 0.0625, 12 * 0.0625)
    };

    public static final PropertyDirection PROPERTYFACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public BlockToaster(String unlocalizedName, String registryName) {
        super(Material.IRON);
        setUnlocalizedName(unlocalizedName);
        setRegistryName(registryName);
        setCreativeTab(Ref.tabModSquad);
        setDefaultState(blockState.getBaseState().withProperty(PROPERTYFACING, EnumFacing.NORTH));
    }


    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, PROPERTYFACING);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        for(EnumFacing e : EnumFacing.Plane.HORIZONTAL)
            if(state.getProperties().get(PROPERTYFACING).equals(e))
                i = e.ordinal();
        return i;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        worldIn.setBlockState(pos, state.withProperty(PROPERTYFACING, placer.getHorizontalFacing().rotateAround(EnumFacing.Axis.Y)));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) { return getDefaultState().withProperty(PROPERTYFACING, EnumFacing.NORTH); }

    //@Override public boolean isOpaqueCube(IBlockState state) {
    //    return false;
    //}
    //@Override public boolean isNormalCube(IBlockState state) {
    //    return false;
    //}
    //@Override public boolean isFullCube(IBlockState state) {
    //    return false;
    //}


    @Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return bounds[(getMetaFromState(state)+1)%2];
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileToaster();
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            TileEntity te = worldIn.getTileEntity(pos);
            if (te instanceof TileToaster) {
                TileToaster toaster = (TileToaster) te;
                if (!playerIn.isSneaking()) {
                    if (heldItem != null && heldItem.getItem() == ModItems.breadSlice) {
                        if (toaster.addSlot1()) {
                            heldItem.stackSize--;
                            return true;
                        }
                    }
                    toaster.removeSlot1();
                } else {
                    toaster.toast();
                    System.out.println(toaster.getToastTimeRemaining());
                }
            }
        }
        return true;
    }
    /*
    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        if (entityIn instanceof EntityPlayer) {
            if (!worldIn.isRemote) {
                EntityPlayer playerIn = (EntityPlayer) entityIn;
                TileEntity te = worldIn.getTileEntity(pos);
                if (te instanceof TileToaster) {
                    TileToaster toaster = (TileToaster) te;
                    playerIn.addChatMessage(new TextComponentString("Power: " + toaster.getStoredPower()));
                    playerIn.addChatMessage(new TextComponentString("Slot1: " + toaster.getSlot1() + " and is toasted: " + toaster.getToast1()));
                    playerIn.addChatMessage(new TextComponentString("Slot2: " + toaster.getSlot2() + " and is toasted: " + toaster.getToast2()));
                    playerIn.addChatMessage(new TextComponentString("Time remaining: " + toaster.getTimeRemaining()));
                }
            }
        }
    }*/
}