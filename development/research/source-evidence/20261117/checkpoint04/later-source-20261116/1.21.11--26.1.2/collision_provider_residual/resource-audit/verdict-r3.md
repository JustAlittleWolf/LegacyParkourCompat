# Modern bundled block-tag provider verdict (r3)

**Verdict: ACCEPTED** for sharing these five vanilla bundled default block-tag payloads between 1.21.11 and 26.1.2. Scope is pinned client JAR defaults only; no claim is made about mod packs, user datapacks, or runtime reloads.

## Recomputed artifact evidence

Raw ZIP entry bytes were SHA-256 hashed. Recursive membership expands referenced block tags to distinct block IDs, sorts IDs ordinally, joins them with LF and no trailing LF, then SHA-256 hashes the result. Both jars matched payload bytes, member sets, counts, and closure hashes for every row.

- `1.21.11.client.jar` SHA-256: `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd`
- `26.1.2.client.jar` SHA-256: `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0`

| Tag | Raw payload SHA-256 | Distinct resolved block IDs | Closure SHA-256 |
|---|---|---:|---|
| `fences` | `214462701d11703b222317fb7d470caa40e017b5036b127db242986b92fb30cd` | 13 | `cdf618b563585cf3ff589b4f6f573266833fc4ece0dfa8ff7d559320d89ba6d1` |
| `wooden_fences` | `b688d023f03758224cfd1bf8b50d018c287aab3bddd939a00abd699fea7ac11f` | 12 | `58c6858e6a88ba6269bf97813926166e6237deff652f5d718783cb5ea312f434` |
| `walls` | `7124e8b44110efc945df32e09bf88dae84413d38558c8073e84e98532391ef97` | 26 | `9cfef012c9ad7070ca6ef7f54b749fba82942b0d7b9c50dd7c944b955952ec66` |
| `wall_post_override` | `c0aff6e7027ae63685e75d05e9d317ac35b8f0e65ef0702a1a0e7e92dd711c34` | 78 | `690cda49e7b3fcc2ad74594480395e2e6a2b8432e3e123257b24b34e397853fb` |
| `shulker_boxes` | `c9d65740aa756f65066575b7b6d810e6f4ba4e3c64ca159ee8aef04ece089aa4` | 17 | `ce709ae2bf84751061173c1c09b4629864441548bab32b18370ffc713a4966f8` |

## Canonical mapped source check

Exact local sources: `decompiled_minecraft/1.21.11/mojmap` and `decompiled_minecraft/26.1.2/unobfuscated`. Relevant consumer behavior agrees: `FenceBlock.isSameFence` checks `FENCES` and wooden classification through `WOODEN_FENCES`; `WallBlock.connectsTo` checks `WALLS`; `WallBlock.shouldRaisePost` checks `WALL_POST_OVERRIDE` and neighboring wall `UP`; `Block.isExceptionForConnection` includes `SHULKER_BOXES` after the same explicit block exceptions.

| Source | 1.21.11 SHA-256 | 26.1.2 SHA-256 |
|---|---|---|
| `Block.java` | `bd7f69ffe617fa1008c9311ad7c731b23f242acb5ff3bab8c1a81e41e7d15a73` | `1693cfb7b84190a2fe664470a56d59e78ed5bd7d722a2bd16888b036d4d8e977` |
| `FenceBlock.java` | `5cd60ac6466efbd64db7971ee2f6fcd519d12794be084afe40d1c4e085065326` | `be5e1aa19a07d68457ff21968fb4e264669588fb6e83a86a6f51e76833f17f93` |
| `WallBlock.java` | `30f65426c97e2507c13e55edbd078ab86f12d24a1496adb80e5d3cd44b2880f4` | `4dc61223dce4023deb3a7986983db677ccf4c89a53778692bacb301927b9b345` |
| `BlockTags.java` | `9db30d02bbb896538a35267b2f0e9c957a00ffdbf20bcc0ea025b5a7bbec38e3` | `edb77ca338c6e831d47b41d799b2f2cad432f34ee465be33a4d1e69edf0f8c1f` |
