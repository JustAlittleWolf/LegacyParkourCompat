# Independent pinned-archive membership check

The two official client JARs were downloaded from the exact URLs in the audited input contract. Before opening entries, each archive SHA-256 was checked against the endpoint pin. The ZIP reader queried and read only the 14 allowlisted `data/minecraft/tags/blocks/<key>.json` entries per JAR; no other archive entry payload was opened or extracted. Each archived byte sequence matched both the contract and the retained extracted packet file. Temporary JARs were removed after this check.

## 1.16.2

Client JAR SHA-256: `19960f4e3c60f2723f361844d2b4020b8bf2160bd91a1e1efde8ce5aea4ff73d`.

| Archive entry | Bytes | SHA-256 |
|---|---:|---|
| `data/minecraft/tags/blocks/banners.json` | 1096 | `4eec08671d813fefaac56d8d46459b94c74328fbaac7bfbc6c064e21889e82a5` |
| `data/minecraft/tags/blocks/climbable.json` | 253 | `33bf334558c77268ba9bac414806b4b3842f8029e860857ff11abaf8447d03ff` |
| `data/minecraft/tags/blocks/fences.json` | 108 | `8c4ebabbf6d088340c70c29091eadfd94d11cd551b193af52ea06cc073bc7483` |
| `data/minecraft/tags/blocks/pressure_plates.json` | 215 | `b2e3605fae2d9da4ab618ac5316cee6d790c9c0884ea93f03786bff8119f81e2` |
| `data/minecraft/tags/blocks/shulker_boxes.json` | 637 | `c854bef377f69d8bbc88d37c641533ae7adae0f6999f72da2fea3e6e98fc9df3` |
| `data/minecraft/tags/blocks/signs.json` | 102 | `b97c92898727585dd94b12843aaff438fa7b9c54a00a185b44ea7b979d9f89a3` |
| `data/minecraft/tags/blocks/soul_speed_blocks.json` | 94 | `c86fbd7bcfa2b94c881f0ba9980a694b28150a5b0b628aec0cfedc552f68994f` |
| `data/minecraft/tags/blocks/standing_signs.json` | 271 | `44db05d7844ed53fcb438fd609a7369335c6d5390306521e60a74783cf23ff86` |
| `data/minecraft/tags/blocks/stone_pressure_plates.json` | 130 | `b31ca5cb18082ae3cc9e1d459db5f7b95a02116ab866e2e288e7fe8fcb78a697` |
| `data/minecraft/tags/blocks/wall_post_override.json` | 233 | `a157be4e08f0ba13957594ef9209328757eeca5a7c2a429392e75e59d6084835` |
| `data/minecraft/tags/blocks/wall_signs.json` | 311 | `b163d4a3fa74c8628f17775cd82703d1225f060dc8ffa259f070594efe4571dc` |
| `data/minecraft/tags/blocks/walls.json` | 643 | `0b9aa2dd6ac31252fcd7786d53a3f79c0f2365998711c38cc19f2e8b788796a2` |
| `data/minecraft/tags/blocks/wooden_fences.json` | 279 | `b1844d51d7afa26334bb118c74c192f1315fc04f86be9ce0c6fa9656ccf74b3e` |
| `data/minecraft/tags/blocks/wooden_pressure_plates.json` | 351 | `4a5a7f3a6e8114721a85c98c815e65c3db8106e9522b4b468481520a36e8e84d` |

## 1.16.3

Client JAR SHA-256: `da85197babd989a7de318774cf5d97bcef95e89a70c9fa9a4c8180463e9589fa`.

| Archive entry | Bytes | SHA-256 |
|---|---:|---|
| `data/minecraft/tags/blocks/banners.json` | 1096 | `4eec08671d813fefaac56d8d46459b94c74328fbaac7bfbc6c064e21889e82a5` |
| `data/minecraft/tags/blocks/climbable.json` | 253 | `33bf334558c77268ba9bac414806b4b3842f8029e860857ff11abaf8447d03ff` |
| `data/minecraft/tags/blocks/fences.json` | 108 | `8c4ebabbf6d088340c70c29091eadfd94d11cd551b193af52ea06cc073bc7483` |
| `data/minecraft/tags/blocks/pressure_plates.json` | 215 | `b2e3605fae2d9da4ab618ac5316cee6d790c9c0884ea93f03786bff8119f81e2` |
| `data/minecraft/tags/blocks/shulker_boxes.json` | 637 | `c854bef377f69d8bbc88d37c641533ae7adae0f6999f72da2fea3e6e98fc9df3` |
| `data/minecraft/tags/blocks/signs.json` | 102 | `b97c92898727585dd94b12843aaff438fa7b9c54a00a185b44ea7b979d9f89a3` |
| `data/minecraft/tags/blocks/soul_speed_blocks.json` | 94 | `c86fbd7bcfa2b94c881f0ba9980a694b28150a5b0b628aec0cfedc552f68994f` |
| `data/minecraft/tags/blocks/standing_signs.json` | 271 | `44db05d7844ed53fcb438fd609a7369335c6d5390306521e60a74783cf23ff86` |
| `data/minecraft/tags/blocks/stone_pressure_plates.json` | 130 | `b31ca5cb18082ae3cc9e1d459db5f7b95a02116ab866e2e288e7fe8fcb78a697` |
| `data/minecraft/tags/blocks/wall_post_override.json` | 233 | `a157be4e08f0ba13957594ef9209328757eeca5a7c2a429392e75e59d6084835` |
| `data/minecraft/tags/blocks/wall_signs.json` | 311 | `b163d4a3fa74c8628f17775cd82703d1225f060dc8ffa259f070594efe4571dc` |
| `data/minecraft/tags/blocks/walls.json` | 643 | `0b9aa2dd6ac31252fcd7786d53a3f79c0f2365998711c38cc19f2e8b788796a2` |
| `data/minecraft/tags/blocks/wooden_fences.json` | 279 | `b1844d51d7afa26334bb118c74c192f1315fc04f86be9ce0c6fa9656ccf74b3e` |
| `data/minecraft/tags/blocks/wooden_pressure_plates.json` | 351 | `4a5a7f3a6e8114721a85c98c815e65c3db8106e9522b4b468481520a36e8e84d` |

Result: 28/28 allowlisted entries found, exact size/hash matched, and exact local extracted bytes matched.
