# Independent source audit: 1.21.11 → 26.1.2 menu and inventory callbacks

Verdict: **ACCEPT** for the bounded NO_DIFF report below.

- Candidate: `build/later-source-20261116/1.21.11--26.1.2/menu_movement_callbacks/coverage.md`
- Candidate SHA-256: `6087C7629F23E777863C0158FBAA66022C169400A2BE05065550E4CB2B4292A2`
- Scope: mount-screen/open-screen; container slot/content/data/close; cursor and player-inventory; merchant-offer; recipe-book add/remove/settings callback paths. This accepts no movement difference in these bounded callback routes only. It does not freeze the pair or certify downstream item behavior, other packets, or other movement areas.

## Exact source identity and integrity

The candidate's ready/provenance identities are correct. `decompiled_minecraft/1.21.11/mojmap.ready.json` SHA-256 is `0AD98D0EBD654650492C97EB58BC324D33864787F0CE10D99FAEC3AE2F5B804B`; its `.sources.sha256` digest is `0C4D83FEF84C101D9DB88F331ACEC7960F3C8346228C28995E6CF57BBFCAE555`, matching the ready marker's `sourceManifestSha256`; provenance SHA-256 is `99C9FB741EB5E4AC8FB5780E2158DA324D7C8B6AFC179D494E5986F78FE29F72`. For 26.1.2, `decompiled_minecraft/26.1.2/unobfuscated.ready.json` SHA-256 is `F24AF0F4D38543C2BD9C19B50F347D4E89D6027050E4B21AE070734843946BB7`; `.sources.sha256` digest is `54AE320660CA911A6D20514C965C33EB414FBEDD1AD6727BBA95FE906AA23C0`, matching its ready marker; provenance SHA-256 is `8D727F880CC91F4A297E148C5B75976303D051457334BC689268FBDF631A012A`.

I recomputed every source hash for all ten Java files cited in the candidate and confirmed it matches the endpoint's source manifest entry. I also checked the dispatch class hashes listed below against each source manifest.

## Dispatch and callback trace

Each listed `Clientbound*Packet.handle(ClientGamePacketListener)` dispatches to the corresponding handler in both releases. The compared client-listener interfaces expose the same callback types and relevant signatures. The exact dispatch destinations are:

| Packet | 1.21.11 and 26.1.2 handler |
|---|---|
| `ClientboundMountScreenOpenPacket` | `handleMountScreenOpen` |
| `ClientboundOpenScreenPacket` | `handleOpenScreen` |
| `ClientboundContainerSetSlotPacket` | `handleContainerSetSlot` |
| `ClientboundContainerSetContentPacket` | `handleContainerContent` |
| `ClientboundContainerSetDataPacket` | `handleContainerSetData` |
| `ClientboundContainerClosePacket` | `handleContainerClose` |
| `ClientboundSetCursorItemPacket` | `handleSetCursorItem` |
| `ClientboundSetPlayerInventoryPacket` | `handleSetPlayerInventory` |
| `ClientboundMerchantOffersPacket` | `handleMerchantOffers` |
| `ClientboundRecipeBookAddPacket` | `handleRecipeBookAdd` |
| `ClientboundRecipeBookRemovePacket` | `handleRecipeBookRemove` |
| `ClientboundRecipeBookSettingsPacket` | `handleRecipeBookSettings` |

`ClientPacketListener` preserves the relevant packet-thread guard and branch order. Mount-screen handling looks up the referenced entity, constructs a horse/nautilus inventory menu, assigns `player.containerMenu`, and opens its screen; it does not call player riding or movement writers. `handleOpenScreen` delegates to `MenuScreens.create`, whose compared implementation creates a menu/screen, assigns `player.containerMenu`, then sets the screen. The open-screen and mount-screen menu construction paths do not call player position/velocity/movement-flag writers or `startRiding`/`stopRiding`/`move`.

Slot updates retain the container-0 hotbar pop-time check, creative inventory test, container-ID guard, slot update and creative remote-slot/broadcast sequence. Content updates initialize the inventory menu for container 0, otherwise only the matching active menu. Data updates only set data for the matching active menu. Cursor updates keep the creative-screen guard. Player-inventory updates write the requested inventory slot. Merchant updates preserve the matching-container and `MerchantMenu` checks and write the same offer/XP/level/progress/restock fields in the same order. Recipe-book add/remove/settings retain their list/flag updates, refresh/rebuild, search update and optional `RecipeUpdateListener` call order.

The compared `AbstractContainerMenu.setItem`, `initializeContents`, `setData`, and `setCarried` methods match in operations and order. Container-close still reaches `LocalPlayer.clientSideCloseContainer`, which calls `super.closeContainer()` and clears the screen; `Player.closeContainer()` only restores `containerMenu = inventoryMenu`. The bounded paths reach the client player, but these handlers/setters do not directly write player position, velocity, movement flags, or riding state.

## Verified source files

The ten candidate-cited source hashes all match the exact manifest entries; the audit's relevant roots are `decompiled_minecraft/1.21.11/mojmap/` and `decompiled_minecraft/26.1.2/unobfuscated/`.

| Relative source path | 1.21.11 SHA-256 | 26.1.2 SHA-256 |
|---|---|---|
| `net/minecraft/network/protocol/game/ClientGamePacketListener.java` | `9457c1f7a57e7dbf4ba83f1f6218c57fa114fb8a58114e0e60c305bdc7254492` | `9857bd02f8fbae5e97110551ee227c60e2fd86b2b22d122e1900dfe9d2813258` |
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `f1ebdb54d717266c894c87381c98bad101980d252de5bae6ca55c37aece1732e` | `eb70d05e4f8429341fc41823eae2cc662560efa1e44c8b729db5477501718d82` |
| `net/minecraft/client/player/LocalPlayer.java` | `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477` | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` |
| `net/minecraft/world/entity/player/Player.java` | `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` |
| `net/minecraft/world/inventory/AbstractContainerMenu.java` | `abed9046242460bff516039868d4a118d30f627cafdf25d30ca5d4fe4c1733ce` | `4f944b69f2be2069ffee9efd9c9f97adac42e7568acf20e18aa874a2f49d71d6` |
| `net/minecraft/network/protocol/game/ClientboundMountScreenOpenPacket.java` | `cc48adc8d27389363ce273e4168a06fb9a6eae57bc152fa01cf6439860c55b05` | `d9979006fa42a893bb3189761b8d7e22fe46fa360b9f63461672a65e1a1dd19a` |
| `net/minecraft/network/protocol/game/ClientboundOpenScreenPacket.java` | `efcf5904b90f7ab17a784a0731fc9fce59a7269f335cbf85b0e0f0270044cd6b` | `bbfb8d324670893e9cd7556c039b571d201ccd4093077b4945dbbd9b70f0991b` |
| `net/minecraft/network/protocol/game/ClientboundContainerSetSlotPacket.java` | `a479422746fd9d50c2e993b6dca20bda1c70482caee57e63d113569beac4f992` | `9d3bf9bd9a553e2d8b8c00f453430fd6eb02c44fa6915d6634636a2ca5fa1c3b` |
| `net/minecraft/network/protocol/game/ClientboundContainerSetContentPacket.java` | `5e18950d9369a8b036762c75f926869e8f463317d7c2928787a1cbdb73320f69` | `fb186d0435deb8c52131a4eec5e146d0a37bbecd0200b7b9a2ceb30503e2fece` |
| `net/minecraft/network/protocol/game/ClientboundContainerSetDataPacket.java` | `fb1bb7fd6df78ff56faea2d5db7874df2f46716086fa103cc39b1f27edffdb17` | `309a100b93c4ca3469f87b3408d604705832293de73ce9239e95a0e07d936334` |
| `net/minecraft/network/protocol/game/ClientboundContainerClosePacket.java` | `472a321b756f489e51b635d442919c22b56af381b6e1c9174244f6ae67ff52f2` | `55f5df5a9437bab5c69b3aed3e10cb098863fcdd02403ee683baf948ef88fe65` |
| `net/minecraft/network/protocol/game/ClientboundSetCursorItemPacket.java` | `2a75ea13f85c081cd0c0ce06de8ee67fa87f75df9311b680203665cf9246235b` | `c2859b5a14ecda5098e5fa4f8e2c10b82cb47a60cb30ed6f598fa4c01dd4fe8e` |
| `net/minecraft/network/protocol/game/ClientboundSetPlayerInventoryPacket.java` | `7b244a10bdfe0a14225933f1fb3170d6532eca0c8e33e6e5e1613be18a29b13a` | `fdc97b98fa365566a67155a99f52d744db2b94b718982ab7e182d2b05640fa10` |
| `net/minecraft/network/protocol/game/ClientboundMerchantOffersPacket.java` | `2e0830378da96c5eda614ed88616c1d55399ba5f012f8478e25a751e1b6c0a9d` | `1a0059641496c136ed40fb764ca25df298141e435fe530aab8a291e1ad4f4c86` |
| `net/minecraft/network/protocol/game/ClientboundRecipeBookAddPacket.java` | `8b6c4fd3fa581becf61e4bf33347dce783877f8f4c8a7ee8fcb814e01ed02f7d` | `8c2f36bda4c2f5740373be747fd66b6c1a33c6180e52c0111d8c575ee5ce0416` |
| `net/minecraft/network/protocol/game/ClientboundRecipeBookRemovePacket.java` | `81c1a98e29f6bd86a9c34c87fb3fc89aaf3f0b316e756b906bb30b2aeb6e8d56` | `523681a8758669caabe685807eb4f3917f455ebffa530f7d1477ac1170da94d0` |
| `net/minecraft/network/protocol/game/ClientboundRecipeBookSettingsPacket.java` | `eb53b82cb95fa75ce25b42bafcbf68efa0730caef733073a4676ff15c748032d` | `d646c0743f00dfce2ecb0438cd1b904757cc60fb5baee9e068c76bedaea066f3` |
| `net/minecraft/client/gui/screens/MenuScreens.java` | `b60276479fa74f14a407af2ad97724ffa3f289b01dc1d26b267c23d1282b4b3f` | `7b0395d08a7e4d2a22e507d168f19c54b7ec3caab6902059550ee6dc46617193` |
| `net/minecraft/world/inventory/HorseInventoryMenu.java` | `675131ca93157943786514d17af2f7d7417838d2ef763d7bfae5de4137da8f15` | `d5abb53df4bccd6fc8f07b021a0387337f60f08a2f39002626d7a577fc4ed65d` |
| `net/minecraft/world/inventory/NautilusInventoryMenu.java` | `324a09d244662215094d83ffbf504295fef1163c3be5684ec81dadbf242361a5` | `f8113a4b7866f0bef1d6e95e5723c55bd52ed57af20da78ce439b53cf5f955a4` |

## Boundedness and remaining gaps

The candidate's NO_DIFF is correct for the listed handlers, dispatch routes, and inherited container setters. The item payloads do change inventory state; whether particular values or equipment slots affect later movement is outside the claim and remains open. Related callbacks such as held-slot selection, opening a book, ghost-recipe placement, and recipe-table replacement are not part of this candidate's explicit list; their behavior is not covered by this verdict. No intermediate release boundary, full movement inventory, or full-pair freeze is established.
