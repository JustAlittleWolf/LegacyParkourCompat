# 1.14.4 wall/leaves raw-resource extraction

- Artifact: Minecraft 1.14.4 raw Mojang client JAR; URL `https://piston-data.mojang.com/v1/objects/8c325a0c5bd674dd747d6ebaa4c791fd363ad8a9/client.jar`.
- Official version metadata: `.minecraft-decompile-cache/1.14.4/version.json`, SHA-256 `615f466a39d2c19ae9b6e2401aa7bd07dbda337f976bcce383326b8d6ba4e532`; client SHA-1 `8c325a0c5bd674dd747d6ebaa4c791fd363ad8a9`, size `25191691` bytes.
- Local raw client SHA-256: `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a`; matches local artifact pin, ready marker `clientSha256`, and version metadata SHA-1/size.
- Ready source identity: 1.14.4 Ornithe Feather Gen2 build.2; source-manifest SHA-256 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`; artifact-manifest SHA-256 `308cc33ef6dffc047432ade6affc88eccc9de94a736ee9a98571fd92915c2970`.
- Extraction used the raw client JAR after verifying its pinned official raw artifact identity; only the two requested ZIP entries were read.

- `build/source-residual-20261116/1.13.2--1.14.4/wall_resource_membership/payloads/data/minecraft/tags/blocks/leaves.json`: SHA-256 `c09bb9af30197720b232b9c84079039d4f344bfc2970fa0d02a1b43bc091b991`; parsed `minecraft:tnt` membership: absent; value count `6`.
- `build/source-residual-20261116/1.13.2--1.14.4/wall_resource_membership/payloads/data/minecraft/tags/blocks/walls.json`: SHA-256 `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c`; parsed `minecraft:tnt` membership: absent; value count `14`.
