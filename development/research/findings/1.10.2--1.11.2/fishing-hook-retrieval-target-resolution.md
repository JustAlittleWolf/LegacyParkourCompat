# Fishing-hook retrieval resolves its local-player target on metadata update

1.10.2, Ornithe Feather Gen2 build 2: `net/minecraft/entity/FishingBobberEntity.java`, `onDataValueChanged`, lines 89–98; client target lookup in `tick`, lines 157–165; `doEvent`, lines 480–486. The entire file is identical in the inspected 1.10 and 1.10.1 sources.
1.11, Ornithe Feather Gen2 build 2: the same file, `onDataValueChanged`, lines 94–102; `doEvent`, lines 431–437.
1.11.1 and 1.11.2, Ornithe Feather Gen2 build 2: the same file, `onDataValueChanged`, lines 106–113; `doEvent`, lines 446–452.

Difference: In 1.10.2, a positive synchronized `HOOKED_ENTITY` value only clears an existing non-null `hookedEntity` in the metadata callback. The next client bobber tick resolves the positive ID to `world.getEntity(id - 1)` if the pointer is null. In 1.11 and the inspected later patches, the metadata callback resolves the ID immediately, or sets the pointer to null for a nonpositive ID. All versions handle entity event 31 by applying the retrieval response only when the pointer currently names a local player.

Movement meaning: If a metadata update names a local player already present on the client and entity event 31 is processed before the bobber's next tick, the old null pointer prevents the direct player velocity response, whereas the newer callback establishes the target before the event and permits the response. This concerns only the gate on the direct local-player response; it does not request historical bobber simulation. The independently changed retrieval impulse formula is a separate operation.

Client reachability: In 1.11.2, `ClientPlayNetworkHandler.handleEntityData`, lines 460–466, applies synchronized data, and `handleEntityEvent`, lines 862–879, forwards event 31. `Minecraft` drains queued packet tasks before ticking (lines 847–859), allowing both packets to be handled in the stated interval. The source comparison establishes the immediate lookup by 1.11 and its persistence through 1.11.2.
