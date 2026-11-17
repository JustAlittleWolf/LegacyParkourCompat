# Final independent verdict: Nether portal inside shape

Terminal status: COMPLETED

## Bound immutable revisions

- `1.21.4--1.21.5/nether-portal-inside-shape.md` — SHA-256 `d2a3d6a7ade61ccd2ede7ba12b47a2efcbce77817b53c4c6bbfef664135caab4` (verified before reading).
- `1.21.5--1.21.8/nether-portal-inside-shape.md` — SHA-256 `497fd00112efc250258ab6d2fea1b88a6c0586d45916f3c774f98d0988041ba2` (verified before reading).
- `comparison.md` — SHA-256 `5479b8f80054de234ebbd5798817406c39fdc060385e5b7d529d42d5b00d7a06` (verified before reading).
- Prior verdict record `verdict.md` — SHA-256 `01acdbab26521767f0a43e7d0c4cbcb5c80bfd3dc72eeb989328ae67e9f35fed`. This final record supersedes the earlier claim-B endpoint `.5–.6` with the revised `.5–.8` finding; it preserves the bounded source verdict.
- Independent exact-source roster `review-roster.md` — SHA-256 `7b6d10a54c075a1515f76ab2e5fa6bab4cb62de1834329227d967cdd87218aaf`. The selected files match their exact local source-manifest/provenance entries; ready-marker source-manifest SHA gates match. The final comparison's source-sidecar identities were read and their cited provider hashes independently recomputed.

## Per-revision verdicts

### 1.21.4–1.21.5: slab-specific inside contact

**ACCEPT.** The finding's scope and source citations remain the ones independently accepted in the prior verdict, now bound to its final immutable hash above. The exact source pair is adjacent. In 1.21.4, `NetherPortalBlock` has an axis-dependent outline but no inside-contact override (`NetherPortalBlock.java` 47–69); inherited `BlockBehaviour.getEntityInsideCollisionShape` returns the `Shapes.block()` singleton (`BlockBehaviour.java` 353–355), which `Entity.checkInsideBlocks` treats as the sentinel to bypass the swept-shape test (`Entity.java` 1050–1084). In 1.21.5, the portal override returns its axis-specific outline (`NetherPortalBlock.java` 49, 61–69); `Entity.checkInsideBlocks` applies the swept test for a non-sentinel (`Entity.java` 1079–1120, 1129–1133). `Block.column` and `Shapes.rotateHorizontalAxis` establish the 4/16-wide, full-height central slab (`Block.java` 168–175; `Shapes.java` 344–350). Portal registration remains `noCollission()` (`Blocks.java` 2024–2030 in .4; 2084–2090 in .5).

Applicability: visited portal cell; live, awake, non-passenger local player; no portal cooldown; block effects enabled; grounded standing width `0.6`; short/stationary movement; center offset `0.05` from the nonslab side. The player box reaches `0.35`, outside the slab boundary `0.375`, while overlapping the portal cell. In .4 the full-cell sentinel dispatches; in .5 the slab test misses. `BlockGetter.boxTraverseBlocks` includes the target box's cells for the short-motion case (`BlockGetter.java` 172–188). This is a static source witness, not runtime validation.

### 1.21.5–1.21.8: first return to the full-cell sentinel is 1.21.6

**ACCEPT, with the revised `.5–.8` endpoint scope and explicit `.6` boundary.** The final finding accurately states .5's slab override, its absence in .6, the inherited full-cell default, and the same provider behavior through .7 and .8. The adjacent `.5`→`.6` cutover is exact: .5 `NetherPortalBlock` SHA-256 `1cb1b0a796f491deb2317d8a2706442741a627582ca86e3cc7adb6345f975d1b` contains the override; .6 SHA-256 `883beec01685148971b2b284be2267b5a6a1f258c49e2da4dd28fb4155d49967` does not. In .6, `BlockBehaviour.getEntityInsideCollisionShape` defaults to `Shapes.block()` (`BlockBehaviour.java` 314–316), and the entity scan bypasses the narrow-shape test for that singleton (`Entity.java` 1113–1162). Thus the finding's visited-cell witness misses in .5 and dispatches again from .6 onward.

The cited .6–.8 equality is accurate. I independently rehashed each exact file and matched each hash to its release manifest/provenance:

| File, 1.21.6–1.21.8 | SHA-256 |
| --- | --- |
| `NetherPortalBlock.java` | `883beec01685148971b2b284be2267b5a6a1f258c49e2da4dd28fb4155d49967` |
| `BlockBehaviour.java` | `15418b0026919ef5ab97780a6ce8844677dc82d36cc4433a58f379b837d5cc50` |
| `Entity.java` | `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704` |
| `Block.java` | `d66c7efd4afb3e52f8d239a1b5fcd00a5c24874d75b61b5b16b794faf200f2c2` |
| `Shapes.java` | `03cd390636f0253b881ddd36c70e61cca2956b02281d45df26c8038ebbdbf63b` |

## Inventory-open grounded witness and gates

**Supported for local client movement.** `Minecraft` opens the survival `InventoryScreen` through `keyInventory` when inventory is not server-controlled (.4 `Minecraft.java` 1840–1845; .5 1861–1866; .6–.8 1872–1877). It descends from `AbstractContainerScreen`, whose `isPauseScreen()` is false (.4/.5 lines 666–668; .6–.8 lines 668–670), so the local world continues ticking. With portal contact pending, `LocalPlayer.aiStep` closes the screen before the following `input.tick` (.4 642–663, 844–866; .5–.8 682–703, 870–892). Opening the screen releases mouse capture; the final draft's additional `!mouseGrabbed` gate is supported: `MouseHandler.grabMouse` repolls only inside the active-window and currently-ungrabbed branch, and only off macOS (.4 359–364; .5–.8 398–403). `KeyMapping.setAll` re-reads held KEYSYM keys (.4/.5/.6 55–58); `KeyboardInput.tick` samples jump (.4/.5 22–31; .6–.8 23–32). LocalPlayer maps the sample to `jumping` (.4 `serverAiStep` 607–617; .5–.8 `applyInput` 608–621); grounded LivingEntity jump can then call `jumpFromGround` (.4 `LivingEntity.aiStep` 2739–2753; .5 2725–2739; .6–.8 2777–2791) and raise Y velocity. Required gates include active window, mouse initially ungrabbed after inventory opened, non-macOS key restoration, a still-physically-held jump key, ground contact, and no jump delay.

Closing the inventory sends a `ServerboundContainerClosePacket`, but LocalPlayer immediately closes the client screen and does not wait for a response (.4 `LocalPlayer.java` 315–323; .5 311–319; .6–.8 305–313). The inspected server handler delegates to menu close only (.4 `ServerGamePacketListenerImpl.java` 1669–1672; .5 1750–1753; .6–.8 1764–1767); this does not invalidate the local key-poll/jump path. Server movement correction/acceptance remains outside the claim and has not been runtime-validated.

## Remaining caveats

- The .6 first-removal version is established by the exact adjacent .5 and .6 source trees. The .5–.8 finding does not imply broader patch boundaries.
- Scanner movement batching/traversal changed across .4–.8. The witness uses a visited portal cell with stationary or short movement; high-speed, axis-recomposition and queue-edge scan behaviors remain separate, unreviewed dependencies.
- The input path is conditional on the stated focus/capture/key/jump gates. No game, TAS, test, build or runtime verification was performed. No conclusion is made about server movement acceptance, implementation, or whole-pair coverage/freeze.
