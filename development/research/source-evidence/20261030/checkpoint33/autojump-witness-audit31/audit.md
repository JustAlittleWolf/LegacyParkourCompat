# Independent audit: auto-jump entity query in 0.2F player poses

Audit identity: exact pair 1.13.2 (Ornithe Feather build 2) and 1.14.4 (Mojmap, Feather build 2), source-only, bounded to the local sleeping/dying pose route. This is not pair coverage. No product source, wiki inventory, product history, tests, builds, gameplay, source generation, or intermediate release was inspected.

Input revision: `.native-source/20261030/autojump-witness31/report-correction.md`, SHA-256 `0D324A412834807BD085C4E559FF3575CEA305CF16E99500E982D6960ED8A3E3`.

## Verdict

The geometric correction is sound: collision shapes are filtered by intersection with the scan, not clipped to it. A tall returned entity shape can therefore intersect both the 0.2F scan and the +0.51F side segment. The collision query bound difference is also real: 1.13.2 searches entities in a box expanded by `0.25` then filters whole shapes against the original scan; 1.14.4 searches and filters whole collide boxes against the scan inflated by `1.0E-7`.

I independently checked the replacement negative reachability proof. For vanilla keyboard movement, it holds at both endpoints. An opened non-null screen calls release-all; both ordinary sleep/death screens keep the default `passEvents=false`. Existing pass-through inventory screens do set `passEvents=true`, but their inherited container `keyPressed` handlers return true for every key press after local handling. The keyboard callback therefore exits before updating key bindings, and `KeyboardInput.tick` recomputes zero movement from those released bindings. The mouse-grab `setAll` path is not a loophole while a screen is open: mouse grab occurs only from the no-screen click path or when a screen is closed. A movement binding remapped to a mouse button could pass an unhandled mouse event through an inventory screen, but that is input-device plumbing, which the project scope excludes.

Packet and UI order also do not create a vanilla same-tick input window. In 1.13.2, the client connection processes packets after world entity ticking; sleep/death status therefore affects the next local tick, whose UI check runs before entity ticking and releases bindings when no screen exists. In 1.14.4, queued main-thread packet work runs before each tick; if health/status changes establish a dying pose then, the null-screen check installs the death screen before local entity ticking. If a screen is already present, it is not replaced by a death/sleep screen, but a pass-through inventory still consumes keyboard movement presses as described above. Local damage does not provide a separate same-tick path: 1.13.2 local-player `takeDamage` returns false, and 1.14.4 `LivingEntity.hurt` rejects client-side damage; authoritative health and death status arrive through packets.

Accordingly, under in-scope vanilla keyboard controls, no nonzero input reaches the auto-jump query from these local 0.2F sleeping/dying pose paths. The entity query cannot assign timer `1` there, so it cannot create the proposed timer-to-jump consequence. The initial height-disjointness proof remains withdrawn; the negative result rests on input and event ordering, not geometry. A hypothetical timer already armed before entering the pose is consumed on the next local input tick, but sleeping/death immobility clears the entity jumping flag before jump processing. Dying ends in respawn/player replacement, so its timer does not carry into alive movement. Ordinary sleeping clears any prior one-tick timer before a later wake tick.

## Independently checked gates and order

- 1.13.2 `PlayerEntity.die(DamageSource)` sets size to `0.2F` by `0.2F`; its sleep path does likewise. 1.14.4 maps `Pose.DYING` and `Pose.SLEEPING` to fixed `0.2F` dimensions; `LivingEntity.die` sets `Pose.DYING`.
- Both local-player move overrides call auto-jump after superclass movement. Required gates are enabled, timer `<= 0`, on ground, not sneaking, not riding/passenger, and nonzero current input. Then the code applies its facing dot gate (`>= -0.15`), two block headspace checks, swept side-segment collision, and obstacle height strictly greater than `0.5F` and at most the current jump-height cap (normally `1.2F`) before setting timer `1`.
- Both providers include entities and retain their full collision shape/box after intersection filtering. The old broad entity enumeration and new epsilon-inflated query/filter are not themselves proof of a player movement witness. The corrected tall-shape geometry is possible, but the preceding keyboard-input gate is false in the requested pose/UI paths.
- 1.13.2 screen auto-open and event order: `Minecraft.tick` sleeping/dead screen logic (1094–1101), `openScreen` release-all (643–663), key event pump gate (1112–1119), world entity ticking (1140–1147), then `connection.tick` (1186–1189). `LocalClientPlayerEntity.move` / `autoJump` (871–886), `KeyboardInput.tick` (13–45), `InventoryMenuScreen.keyPressed` (523–543), and `LivingEntity` immobile jump clear (1891–1896).
- 1.14.4 order: `runTick` executes queued tasks before `tick` (828–840); `Minecraft.tick` checks screens before key/event input and `level.tickEntities` (1260–1309). `setScreen` releases mappings (729–750). The key-event pump uses null-or-`passEvents` (1282–1289), but `AbstractContainerScreen.keyPressed` consumes every key (533–553). `KeyboardInput.tick` reads mappings (13–26); `LivingEntity.aiStep` / `Player.isImmobile` clear jumping before jump handling (2188–2206; Player 980–982).
- The ordinary sleeping screen is `SleepingChatScreen` / `InBedChatScreen`, both derived from `ChatScreen` and inheriting false `passEvents`; death screens also inherit false. On wake, closing the bed screen may repopulate physical bindings via `grabMouse`/`setAll`, but a one-tick timer cannot survive the earlier sleeping tick that decremented it and immobility-cleared jumping.

## Exact source identities

Hashes are SHA-256 from the read-only shared source roots; selected movement/query identities independently match the supplied correction/supplement.

| Release/mapping | Relative source | SHA-256 |
|---|---|---|
| 1.13.2 Feather | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `2583495F3A02B4791AA036E6A8D354D7596C4984761969A0F29B29D8D9BF42BF` |
| 1.13.2 Feather | `net/minecraft/client/Minecraft.java` | `B1D35473CD12BDF38E87F9888B4ED6F620614CBE914CF7E7A40C601D4E284875` |
| 1.13.2 Feather | `net/minecraft/client/MouseHandler.java` | `B045DC9EAF5B5AB1D9F13DD795465A106F52571DDA9E5C837F5C2BBBDFDDF4CE` |
| 1.13.2 Feather | `net/minecraft/client/KeyboardHandler.java` | `25AE003CF26150AF1EAA66D3EE030BD3C85DC2983118F90600945EC74EDA853B` |
| 1.13.2 Feather | `net/minecraft/client/options/KeyBinding.java` | `1093DFB57D0D8EEC8261201E32235C11456ECE48CFAD8CC130B64E3866B308FB` |
| 1.13.2 Feather | `net/minecraft/client/entity/living/player/KeyboardInput.java` | `7BE11425906BE051C83E275F359816546E4677B16D212156380E8D2E9258654A` |
| 1.13.2 Feather | `net/minecraft/client/gui/screen/Screen.java` | `0EE99A0739CB677566E12231D7841D7242428F96DE0E4E35A09E6E3DFCDB61DF` |
| 1.13.2 Feather | `net/minecraft/client/gui/screen/game/SleepingChatScreen.java` | `7CA21F3D96AFBD656CAF55757F9C80756AFC1C1043DEC60D4DA7F0A0E789E0D9` |
| 1.13.2 Feather | `net/minecraft/client/gui/screen/game/DeathScreen.java` | `5867C49C9389C2E0E9768C740F5F3EBA9F81F0C1F1F67F9A985654DA4D893400` |
| 1.13.2 Feather | `net/minecraft/client/gui/screen/game/inventory/SurvivalInventoryScreen.java` | `495AB1733B5557A092AF28DEAF2AC45F048A679D6DDF323420C71FAD8E733ECF` |
| 1.13.2 Feather | `net/minecraft/client/gui/screen/game/inventory/InventoryMenuScreen.java` | `42327CFE61B7D762A8EECC69FC214CF20A302605ABA511A24A18C3461F28804D` |
| 1.13.2 Feather | `net/minecraft/client/entity/living/player/Input.java` | `9E704CFE7FDC55C4EAB78670E60CF392BA6817ADBD5E7451D3A86F11DA8BF50E` |
| 1.13.2 Feather | `net/minecraft/entity/living/LivingEntity.java` | `BB691358C9A43C9F46E85575BF4D0A4AD671D0EB912502ACC3A6A3F625E42F1C` |
| 1.13.2 Feather | `net/minecraft/entity/living/player/PlayerEntity.java` | `4ED22F6C5A3C55D67EED782070AC722201DF4D624ADBC90779F1FD29C2876633` |
| 1.13.2 Feather | `net/minecraft/world/EntityView.java` | `2B5B59EEF6EFA93BB51C1D838C7A1CF64725BE96C55C47F76894FDE11E13D6DE` |
| 1.13.2 Feather | `net/minecraft/world/World.java` | `0023DE30DA608DB73C6C6188334D2BB19039D86FE4D6F5E4AD79D732A5D2CD1A` |
| 1.13.2 Feather | `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `4F65502FBEE6476CEF0838563F03A66F07A1A9ED8C63E580F5338BA5FE274F11` |
| 1.14.4 Mojmap | `net/minecraft/client/player/LocalPlayer.java` | `0795C1223198CE5ACF5D2ED9E5DB8435BBEC4CD52B96F96B1DDF2865BAAAE85F` |
| 1.14.4 Mojmap | `net/minecraft/client/Minecraft.java` | `B9C368CAB9F09CB9760A9EF3F2C0B7056401F25D7901645B6CA3F08460D79779` |
| 1.14.4 Mojmap | `net/minecraft/client/MouseHandler.java` | `E0FEA181777732B1850C16D2FE7F757DD21170269229ED5C7271379AAEB868D0D4` |
| 1.14.4 Mojmap | `net/minecraft/client/KeyboardHandler.java` | `B51FBAF7F6AC3884AAF109CB689C320238E5D3E9C1703F2761379AAEB868D0D4` |
| 1.14.4 Mojmap | `net/minecraft/client/KeyMapping.java` | `39726C02AC77713483B2A7CF0B751CAFC0E4270084DFEC1547658E8D9A51881D` |
| 1.14.4 Mojmap | `net/minecraft/client/player/KeyboardInput.java` | `33DAA0833A95E09E728C1B8F509020DD13B70E38ABA922E2B1E10EC5C9B7739A` |
| 1.14.4 Mojmap | `net/minecraft/client/gui/screens/Screen.java` | `83810BB9A85F2DE55D5A2765FAFDC77425BB32E78B3F0AEBA11330FE1F7A1AF3` |
| 1.14.4 Mojmap | `net/minecraft/client/gui/screens/InBedChatScreen.java` | `2C8BEF4339D6072064A072E41BEA2B80D4636A93AD78FA486E77C0E87393F144` |
| 1.14.4 Mojmap | `net/minecraft/client/gui/screens/DeathScreen.java` | `6C0BF438584C4DF4B7082D4DB7BCB74CD9D24FC39E5605FA3BF8D846493B4F29` |
| 1.14.4 Mojmap | `net/minecraft/client/gui/screens/inventory/InventoryScreen.java` | `3F5BD0E229A7C39B56BFF5942F3C2153655BE1A1CE462E72D1BF609236DB5D61` |
| 1.14.4 Mojmap | `net/minecraft/client/gui/screens/inventory/AbstractContainerScreen.java` | `05F95079DD91C0D5A7C8C69ACB09982C1759BF42AF27F18EA8DD99EA5DC4F62B` |
| 1.14.4 Mojmap | `net/minecraft/client/player/Input.java` | `8ADAF34FA0BCEF319D8B3DD678F53DDF68B0293A17E2C1620A6528FA0D9AD1EF` |
| 1.14.4 Mojmap | `net/minecraft/world/level/EntityGetter.java` | `DD7745C85C83BF327346008E815C64AA5D0E0BA253EF9640BE48223824F0DFC6` |
| 1.14.4 Mojmap | `net/minecraft/world/entity/LivingEntity.java` | `428762178A876EFD4069086E6B7D51F571EA0401F44E7EFF0927B7D26D9EF681` |
| 1.14.4 Mojmap | `net/minecraft/world/entity/player/Player.java` | `E9CE5EB18C5581FFE2B610B273FFD85786587A00BA853E5DBA0AC96593622B12` |
| 1.14.4 Mojmap | `net/minecraft/client/multiplayer/ClientPacketListener.java` | `6DDDF37B79830D5413D91675E0BED8B4A3F0A179AC4535616DC57B510B778B45` |
