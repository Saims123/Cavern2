package cavern.network.client;

import cavern.network.CaveNetworkRegistry;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public interface IPlayerMessage<REQ extends IPlayerMessage<REQ, REPLY>, REPLY extends IMessage> extends IMessage, IMessageHandler<REQ, REPLY>
{
	@Override
	default void fromBytes(ByteBuf buf) {}

	@Override
	default void toBytes(ByteBuf buf) {}

	REPLY process(EntityPlayerSP player);

	@Override
	default REPLY onMessage(REQ message, MessageContext ctx)
	{
		IThreadListener thread = FMLCommonHandler.instance().getWorldThread(ctx.netHandler);
		EntityPlayerSP player = FMLClientHandler.instance().getClientPlayerEntity();

		if (thread.isCallingFromMinecraftThread())
		{
			return message.process(player);
		}

		thread.addScheduledTask(() ->
		{
			REPLY result = message.process(player);

			if (result != null)
			{
				CaveNetworkRegistry.sendToServer(result);
			}
		});

		return null;
	}
}