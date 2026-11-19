# Independent 26.1 → 26.1.1 source audit

Verdict: complete scoped source comparison with zero independent client-player movement differences. No finding draft. Runtime parity is unverified.

## Evidence

Both releases use published unobfuscated names and Vineflower 1.12.0. For each release I enumerated and SHA-256 rehashed all 6,882 local Java files, checked the roster and byte digests against the sibling publication manifest, then checked that manifest and the artifact manifest against the ready marker. The source publication contains only Java files, so I also checked the pinned original client JARs independently by SHA-1 and SHA-256 and hashed the contents of every non-directory entry. Each JAR has the same 29,435-entry file roster.

| Release | Source manifest SHA-256 | Artifact manifest SHA-256 | Client SHA-256 |
| --- | --- | --- | --- |
| 26.1 | b77289c135ac6e12aa35e75177cea52d968f49d49e1ec745e510b1a786c9aeba | 03e2146ccc20c3f873cea447cd9a5958cf8137524d66641121d6af677a62d2a0 | bc6194c61566b587f196090d730698f2191170f7580adb143e4ff839939d3840 |
| 26.1.1 | 88afdfb8264842713f8cce86c0bf88e95c2c991ee706349d6ed8b6c22d52fcb3 | 6ee17e11c2cf02a4ba93d66852ea172d5ba857028654fb5df3c1d9c87f0faa81 | 8c109be6177f40d10d0afd820b06542f9609597874242b96e16c5b0dc9e1fef3 |

Verified SHA-1s are `191771837687b766537a8c4607cb6fad79c533a1` and `377031a9e733ba8ab4d355959a8f6fb8eb707556` respectively. The 77-entry artifact inventories have equal dependency path/digest sets after stripping the release prefixes and omitting the two release-specific artifacts, the client and launcher version metadata. The sibling provenance and artifact manifests agree on launcher metadata digest and release ID. Separate launcher JSON files were pending when I checked. The JAR evidence is independently verified.

## Movement coverage

The whole-input equivalence check covers transitive providers, including overrides, math, registered state and packaged resources. The source anchors below have intact bodies and exact byte identity in both releases. Paths are relative to either release's `unobfuscated/net/minecraft/` root.

| Reachability slice | Paired anchors and dependent inputs |
| --- | --- |
| Client dispatch and input | `client/Minecraft.java:1788,1857,1882`; `client/multiplayer/ClientLevel.java:330–351`; `client/player/LocalPlayer.java:227,767`; `client/player/KeyboardInput.java`. Checked client tick and entity/ride dispatch, pre-travel input, crouch, sprint, use scaling, jump buffering, ability and flying gates. |
| Inherited tick and travel | `world/entity/player/Player.java:231,342–372,442,1381–1412`; `world/entity/LivingEntity.java:390,2352–2650,2699,2976–3077`; `world/entity/Entity.java:499–524`. Checked ground/air, liquid, player ability flight, gliding, jumping, gravity, friction, clamping, climbing, timers, block effects and post-travel push. |
| Position, velocity, boxes and collision | `world/entity/Entity.java:460–464,659–839,1015–1150,3302–3320,3668–3680`; `client/player/LocalPlayer.java:748,986–1121`; `world/level/BlockCollisions.java`. Checked edge backing, supporting block, clipping and step-up, auto-jump, dimensions and pose, collision flags, fall handling, bounding box/position writes, border, shape providers and voxel math. |
| Block and fluid providers | `world/entity/Entity.java:839–944,1548–1651`; `world/entity/EntityFluidInteraction.java`; `world/level/block/piston/PistonMovingBlockEntity.java`. Checked inside/step/fall callbacks, piston pushes, liquid height/eye and flow, staggered block effects, registrations, contextual shapes and neighboring state, fluids, block/item/entity tags and state-dependent factors. |
| Modifiers and direct external responses | `client/multiplayer/ClientPacketListener.java:624,633,669,796,853,939,1361,1795,2087,2265,2355`; `world/entity/Entity.java:1789–1826`. Checked packet position/velocity and reconciliation, synchronized state, push/knockback, explosion impulse, interactions with mounts and other entities, equipment, enchantments, attributes, effects, pose, item slowdown and abilities. Server packet producers are covered as supplied inputs where relevant to the client response. |

## Dispositions for all changed inputs

- `client/gui/screens/social/PlayerEntry.java:98,112` negates the chat-receive predicate for a report screen flag. The constructor passes it to `ReportPlayerScreen`. It does not modify a player-state field or the input/tick machinery.
- `SharedConstants.java:14–24,204–206` changes the world version and unused snapshot protocol constant. The release protocol continues to return 775. `DetectedVersion.java:26–42` updates the fallback world version. Bundled `version.json` changes only ID, display name, world version and build time. The pack versions and release protocol are unchanged. Versioned `KnownPack` identity can affect configuration-time selection between matching local known-pack data and transmitted registry entries. `server/packs/repository/KnownPack.java:20–21`, `core/RegistrySynchronization.java:30–59`, and `client/multiplayer/ClientConfigurationPacketListenerImpl.java:97–118` close this branch. The relevant built-in registry and tag contents have identical bytes.
- All 1,202 changed `data/minecraft/structure/*.nbt` descendants have byte-identical gzip-decompressed data after removing the four-byte integer value of the `DataVersion` field, 4786 → 4788. This comparison retains palettes, blocks, block states, entities, NBT, positions and order. `world/level/levelgen/structure/templatesystem/loader/TemplateSource.java:119–120` applies the STRUCTURE fixer before load. The identical `util/datafix/DataFixers.java:1623–1630,1645–1651` caps its schema/fix registration sequence at 4773 and changes the builder target through the world metadata. Neither bound crosses a registered fix, including when older templates are upgraded.
- The three remaining changed archive entries are `META-INF/MANIFEST.MF`, `META-INF/MOJANGCS.RSA` and `META-INF/MOJANGCS.SF`. They are packaging/signing material. All other executable and resource contents are byte-identical.
