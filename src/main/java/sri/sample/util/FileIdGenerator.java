package sri.sample.util;

import java.util.UUID;

/**
 * @author Srinivas
 * @since 15 March 2017
 */

public class FileIdGenerator
{
	// made this synchronized to ensure no two requests come in at the same instance of time
	public static synchronized String generateFileId()
	{
		return UUID.randomUUID().toString();
	}
}
