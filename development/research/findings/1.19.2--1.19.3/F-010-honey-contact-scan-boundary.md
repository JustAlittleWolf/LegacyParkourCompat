# F-010: Honey Block contact scan reaches a narrower player-overlap band

1.19.2, mojmap: `1.19.2/mojmap/net/minecraft/world/entity/Entity.java`, move 657; tryCheckInsideBlocks 685-694; checkInsideBlocks 886-912; class declaration / field initialization 190-214.
1.19.3, mojmap: `1.19.3/mojmap/net/minecraft/world/entity/Entity.java`, move 657; tryCheckInsideBlocks 685-694; class declaration / field initialization 190-214.
1.19.2, mojmap: `1.19.2/mojmap/net/minecraft/world/level/block/HoneyBlock.java`, entityInside 56-64.
1.19.3, mojmap: `1.19.3/mojmap/net/minecraft/world/level/block/HoneyBlock.java`, entityInside 56-64.
1.19.2, mojmap: `1.19.2/mojmap/net/minecraft/world/level/block/Blocks.java`, class declaration / field initialization 3369-3378.
1.19.3, mojmap: `1.19.3/mojmap/net/minecraft/world/level/block/Blocks.java`, class declaration / field initialization 3369-3378.
1.19.2, mojmap: `1.19.2/mojmap/net/minecraft/world/entity/EntityType.java`, class declaration / field initialization 527.
1.19.3, mojmap: `1.19.3/mojmap/net/minecraft/world/entity/EntityType.java`, class declaration / field initialization 527.
1.19.2, mojmap: `1.19.2/mojmap/net/minecraft/world/entity/EntityDimensions.java`, makeBoundingBox 21-25.
1.19.3, mojmap: `1.19.3/mojmap/net/minecraft/world/entity/EntityDimensions.java`, makeBoundingBox 21-25.

Difference: After `Entity.move` resolves movement, it calls `tryCheckInsideBlocks`, whose wrapper calls `checkInsideBlocks`. Both versions use the resulting entity AABB to construct min/max block positions and iterate the covered cells inclusively before invoking each block state's `entityInside` callback. 1.19.2 insets all six bounds by `0.001`; 1.19.3 insets them by `1.0E-7`. Therefore a cell just across a horizontal AABB edge can be visited in 1.19.3 but omitted in 1.19.2 when the overlap is greater than `1.0E-7` and less than `0.001`. Both versions register the same Honey Block and have byte-identical Honey Block callback code. Its collision shape is `Block.box(1.0, 0.0, 1.0, 15.0, 15.0, 15.0)`, so the solid X/Z bounds begin `1/16` inside the cell. `Block.box` divides each coordinate by `16.0`; the shape is not solid in the first `1/16` of the cell next to each horizontal face. The normal collision path gathers the Honey shape and resolves movement against its voxel cells; the box in the witness below remains entirely in this side gap, so downward movement is not clipped by Honey. When the additional 1.19.3 cell is Honey, `entityInside` calls `isSlidingDown`. That method returns false for a grounded entity, an entity above block Y + `0.9375 - 1.0E-7`, or vertical velocity at least `-0.08`. It then requires either horizontal center separation plus `1.0E-7` to exceed `0.4375 + entity width/2.0F`. For the default player width `0.6F`, the side-overlap witness below satisfies that center-distance test. The callback calls `doSlideMovement`, which immediately sets Y velocity to `-0.05`; when prior Y is below `-0.13`, it scales X/Z by `-0.05 / y`, otherwise preserves X/Z. It then resets fall distance. These callback predicates and writes are identical; the difference is whether the Honey cell is visited.

Movement meaning: The paired sources provide a vanilla command-to-client-motion witness for the overlap, rather than assuming the narrow box state. In a loaded interior chunk containing Honey at block `(1,64,1)` and air in the other cells traversed by the player box, an authorized same-world `/tp @s 0.7005 64.9 1.5` supplies absolute in-bounds coordinates. `TeleportCommand.teleportToPos` leaves the position-relative flags empty for absolute coordinates, then `performTeleport` passes those values to the same-level player's connection. The command's spawnable-bounds guard accepts block position `(0,64,1)`. `ServerGamePacketListenerImpl.teleport` sends the absolute coordinates; `ClientPacketListener.handleMovePlayer` sets the client player's coordinates, clears velocity on absolute axes and rebuilds the box (`absMoveTo` in 1.19.2, `setPos` in 1.19.3). The x coordinate is far inside both entity coordinate clamps. The client handler does not write the local `onGround` flag; take the client player airborne before this correction. `EntityType.PLAYER` registers width `0.6F` and height `1.8F`. `EntityDimensions.makeBoundingBox` uses the float expression `width / 2.0F` and places the bottom at the entity Y. At the stated target, the resulting box is approximately X `[0.4005,1.0005]`, Y `[64.9,66.7]`, Z `[1.2,1.8]`; retain the source float half-width and coordinate arithmetic rather than replacing `0.6F` with a double literal. The Honey collision shape begins at X `1 + 1/16 = 1.0625`; the box's maxX `1.0005` is before that solid interval. With zero horizontal input, there is no Honey X/Z collision, and no other solid shapes in the witness arrangement. `Entity.collide` therefore leaves the requested downward displacement uncut; `Entity.move` updates position/box and, after ground/fall/step callbacks, calls `tryCheckInsideBlocks`.



## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "partial",
    "changes": [
      "src/main/java/me/wolfii/legacyparkourcompat/change/v1_18_2/InsideBlockContact.java"
    ],
    "hooks": [
      "InsideBlockContactHook"
    ],
    "limits": [
      "Old0.001 inset operation exists, but current V1_19 groups1.19-1.19.3 and cannot represent the accepted1.19.2/1.19.3 source boundary. Separate profile decision required; no enum/resolver refactor admitted."
    ]
  },
  "applicability": [
    "First changed stable release1.19.3; authorized in-bounds /tp followed by local gravity witness. Ordinary walking entry/development-build onset remain unproved."
  ],
  "sources": [
    {
      "version": "1.19.2",
      "mapping": "mojmap",
      "path": "1.19.2/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "move",
      "lines": "657",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76 *1.19.2/client_mappings.txt. Canonical source-manifest SHA-256 95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.3",
      "mapping": "mojmap",
      "path": "1.19.3/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "move",
      "lines": "657",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b *1.19.3/client_mappings.txt. Canonical source-manifest SHA-256 6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843 matches archived publication; cited file hash is present in that manifest. Historical literal654898 is a transcription error; actual654498 file digest is bound by the unchanged exact source manifest."
    },
    {
      "version": "1.19.2",
      "mapping": "mojmap",
      "path": "1.19.2/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "tryCheckInsideBlocks",
      "lines": "685-694",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76 *1.19.2/client_mappings.txt. Canonical source-manifest SHA-256 95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.3",
      "mapping": "mojmap",
      "path": "1.19.3/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "tryCheckInsideBlocks",
      "lines": "685-694",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b *1.19.3/client_mappings.txt. Canonical source-manifest SHA-256 6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843 matches archived publication; cited file hash is present in that manifest. Historical literal654898 is a transcription error; actual654498 file digest is bound by the unchanged exact source manifest."
    },
    {
      "version": "1.19.2",
      "mapping": "mojmap",
      "path": "1.19.2/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "checkInsideBlocks",
      "lines": "886-912",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76 *1.19.2/client_mappings.txt. Canonical source-manifest SHA-256 95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.2",
      "mapping": "mojmap",
      "path": "1.19.2/mojmap/net/minecraft/world/level/block/HoneyBlock.java",
      "member": "entityInside",
      "lines": "56-64",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 5ffc5f58a81f82823305c7ed02f9a465c7b3bfd2c1799da566e1485d0cd99ad5; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76 *1.19.2/client_mappings.txt. Canonical source-manifest SHA-256 95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.3",
      "mapping": "mojmap",
      "path": "1.19.3/mojmap/net/minecraft/world/level/block/HoneyBlock.java",
      "member": "entityInside",
      "lines": "56-64",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 5ffc5f58a81f82823305c7ed02f9a465c7b3bfd2c1799da566e1485d0cd99ad5; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b *1.19.3/client_mappings.txt. Canonical source-manifest SHA-256 6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.2",
      "mapping": "mojmap",
      "path": "1.19.2/mojmap/net/minecraft/world/level/block/Blocks.java",
      "member": "class declaration / field initialization",
      "lines": "3369-3378",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 f532585f836ee7ad685d368889ed13d735c570ef38971db0c1d2a7debb4cf27b; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76 *1.19.2/client_mappings.txt. Canonical source-manifest SHA-256 95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.3",
      "mapping": "mojmap",
      "path": "1.19.3/mojmap/net/minecraft/world/level/block/Blocks.java",
      "member": "class declaration / field initialization",
      "lines": "3369-3378",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 9944877c941fa209d2a8b9a7d4eb6333edf2dfca255a3e90601242483eb7a476; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b *1.19.3/client_mappings.txt. Canonical source-manifest SHA-256 6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.2",
      "mapping": "mojmap",
      "path": "1.19.2/mojmap/net/minecraft/world/entity/EntityType.java",
      "member": "class declaration / field initialization",
      "lines": "527",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 4d2fb12f5458e43fcf94ec8f40058f0ef5ba545049fd5a73c0cf755cb4764836; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76 *1.19.2/client_mappings.txt. Canonical source-manifest SHA-256 95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.3",
      "mapping": "mojmap",
      "path": "1.19.3/mojmap/net/minecraft/world/entity/EntityType.java",
      "member": "class declaration / field initialization",
      "lines": "527",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 b63be8727a2fa406bef2ae45c1c9ebe06a56440e01b6dc85c443cbb738c32c06; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b *1.19.3/client_mappings.txt. Canonical source-manifest SHA-256 6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.2",
      "mapping": "mojmap",
      "path": "1.19.2/mojmap/net/minecraft/world/entity/EntityDimensions.java",
      "member": "makeBoundingBox",
      "lines": "21-25",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 ced817743f3bd35462c94c0112adf6f54eabf6132b0a349b05160e519bad3502; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76 *1.19.2/client_mappings.txt. Canonical source-manifest SHA-256 95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.3",
      "mapping": "mojmap",
      "path": "1.19.3/mojmap/net/minecraft/world/entity/EntityDimensions.java",
      "member": "makeBoundingBox",
      "lines": "21-25",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 ced817743f3bd35462c94c0112adf6f54eabf6132b0a349b05160e519bad3502; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b *1.19.3/client_mappings.txt. Canonical source-manifest SHA-256 6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.2",
      "mapping": "mojmap",
      "path": "1.19.2/mojmap/net/minecraft/util/Mth.java",
      "member": "floor",
      "lines": "65-68",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 61727897223a78846a5f1fa270d57aa714c21b3c8d6f2b0e12fea4a07e6c327f; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76 *1.19.2/client_mappings.txt. Canonical source-manifest SHA-256 95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.2",
      "mapping": "mojmap",
      "path": "1.19.2/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "class declaration / field initialization",
      "lines": "190-214",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76 *1.19.2/client_mappings.txt. Canonical source-manifest SHA-256 95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.3",
      "mapping": "mojmap",
      "path": "1.19.3/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "class declaration / field initialization",
      "lines": "190-214",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b *1.19.3/client_mappings.txt. Canonical source-manifest SHA-256 6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843 matches archived publication; cited file hash is present in that manifest. Historical literal654898 is a transcription error; actual654498 file digest is bound by the unchanged exact source manifest."
    },
    {
      "version": "1.19.2",
      "mapping": "mojmap",
      "path": "1.19.2/mojmap/net/minecraft/server/commands/TeleportCommand.java",
      "member": "teleportToPos",
      "lines": "188-229",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 3abf6877a502398a71ce00be3f4f0149e0c9d5c07095b89336580bd6995e6c6a; matches an explicit archived source hash. Reassociated the archived range with its actual owning class/member; original multi-class paragraph remains immutable. Official exact client-mapping manifest binding: c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76 *1.19.2/client_mappings.txt. Canonical source-manifest SHA-256 95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.2",
      "mapping": "mojmap",
      "path": "1.19.2/mojmap/net/minecraft/server/network/ServerGamePacketListenerImpl.java",
      "member": "teleport",
      "lines": "994-1010",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 976992cd0cf4ce387e12543861aab66cc7cf8a58f078f00e0fe8f91df47b4cd2; matches an explicit archived source hash. Reassociated the archived range with its actual owning class/member; original multi-class paragraph remains immutable. Official exact client-mapping manifest binding: c5db94c44c1ce6c5d3bfce64152831090310c202f4abe4375adbb3454afcec76 *1.19.2/client_mappings.txt. Canonical source-manifest SHA-256 95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.19.3",
      "mapping": "mojmap",
      "path": "1.19.3/mojmap/net/minecraft/server/network/ServerGamePacketListenerImpl.java",
      "member": "teleport",
      "lines": "988-1004",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 81288e1e99d88796e096c1386e8a1f108a077078467d4843a56d14f7cb48006b; matches an explicit archived source hash. Reassociated the archived range with its actual owning class/member; original multi-class paragraph remains immutable. Official exact client-mapping manifest binding: f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b *1.19.3/client_mappings.txt. Canonical source-manifest SHA-256 6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [
    {
      "verdict": "ACCEPT, bounded stable/command witness",
      "assertion_preserved": true,
      "review_commit": "dded99fb6d78d4d253ab7f367edc029e981b6b91",
      "review_sha256": "7f54077b4570180d26f0f9a4028bae7ffee1f0dd5582a82c7330a5828608ba3d",
      "snapshot": "b66cde90c5f34970aa94553f838ec8c65e764ea6",
      "snapshot_sha256": "c37c6544856bd4dde35e36801383fe9249044ad4c02341984a8ae8205e217039",
      "identity": "4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/reconciliation/additional-evidence/7f54077b4570180d26f0f9a4028bae7ffee1f0dd5582a82c7330a5828608ba3d.md"
    }
  ],
  "openIssues": [
    {
      "missing": "Current V1_19 group cannot distinguish accepted1.19.2/1.19.3 behavior; earlier aliases and effects of new keys on each existing operation require a corrected decision table.",
      "next_action": "Use corrected resolver analysis in branch-doc-review-late.md before a separately authorized profile change."
    }
  ],
  "runtime": "unverified"
}
```
