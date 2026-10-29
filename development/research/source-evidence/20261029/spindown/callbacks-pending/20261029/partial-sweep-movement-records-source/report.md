# Movement-record source sweep

Scope: canonical decompiled sources for 1.21.2, 1.21.3, 1.21.4, and published unobfuscated 26.2. This bounded report covers ordinary player movement-record production, queue handoff into block-effect processing, collision-axis order where it affects record interpretation, and the ordinary LocalPlayer tick path. Piston origin/cleanup, contact visitation/budgets/fluid callbacks, and shared-query-box geometry are excluded. This is not a pair-freeze, implementation-acceptance, or runtime-parity claim.

## Source identity and integrity

All paths below are beneath D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/.

| Release / namespace | Identity evidence |
| --- | --- |
| 1.21.2 / Mojmap tree | 1.21.2/mojmap/.decompile-provenance.json SHA-256: 00f399407dc447737ea83e3e46562418f8c712f66e3f51cb85d46afa2ecac575. It contains an embedded source-file hash map matching the inspected files below. Inputs include version metadata URL piston-meta.mojang.com/v1/packages/c14db17c663edf3d7a698488263a21b1df6d02af/1.21.2.json (SHA-256 793e3fd0451354a26aaa00253dcb6600add11f9d2a90f7bdef75ddf5b38c2b45), client jar SHA-256 613091da0f62426ae8dcf9afb30eb639485f493b81cda3a2b96b1fb8a6b9976c, and client mapping text SHA-256 e37560e4889168916a73dbc4922c563002eff70fbecfe1cf5c50a7b222f451b2. The tree has no separate ready marker or source-manifest file. |
| 1.21.3 / Mojmap | mojmap.ready.json reports ready, version 1.21.3, Mojmap, 5,655 sources. Source manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches the ready marker. Provenance SHA-256 24093437c0cd41d807ab0829068ef97828b65bbb95b38dfdc4f706883a8483d7. |
| 1.21.4 / Mojmap | mojmap.ready.json reports ready, version 1.21.4, Mojmap, 5,744 sources. Source manifest SHA-256 f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 matches the ready marker. Provenance SHA-256 ab0439864c89f4e0dab09daae7b57ab973368eeb0320626822e06f5a9bce9fb9. |
| 26.2 / unobfuscated | unobfuscated.ready.json reports ready, version 26.2, unobfuscated, 7,055 sources. Source manifest SHA-256 a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894 matches the ready marker. Provenance SHA-256 a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1. |

### Inspected source-file SHA-256

| Release / namespace | Entity.java | LivingEntity.java | Player.java | LocalPlayer.java |
| --- | --- | --- | --- | --- |
| 1.21.2 / Mojmap | a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9 | 087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52 | a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de | fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0 |
| 1.21.3 / Mojmap | a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9 | 087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52 | a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de | fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0 |
| 1.21.4 / Mojmap | 05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad | e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30 | c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1 | 145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611 |
| 26.2 / unobfuscated | 7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5 | 7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a | 8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531 | 8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6 |

The listed 1.21.2 files match the embedded hash map; the other listed files match their published source-manifest entries.

## Source facts

Paths below are relative to each release and namespace root.

### 1.21.2 and 1.21.3

- Entity.java: move(MoverType, Vec3), lines 619–701, resolves the request through collision and updates position but appends no movement record. recordMovementThroughBlocks(from,to), lines 1034–1036, simply appends a Movement(from,to) to movementThisTick; the record has only from and to (line 3608).
- applyEffectsFromBlocks() delegates with oldPosition and current position (736–738). The overload (740 onward) appends that segment when isAffectedByBlocks is true, passes the raw list to checkInsideBlocks, then clears the movement list. It has no zero-length guard for the appended record.
- collideWithShapes (981–1015) resolves Y first, then chooses whether Z or X is first with abs(x) < abs(z); it moves the working box after the first horizontal axis and returns one resolved Vec3. This is collision-resolution order, not movement-record splitting.
- LocalPlayer.tick calls super.tick (LocalPlayer.java 189–192). LivingEntity.tick invokes virtual aiStep while not removed; the LocalPlayer/Player super chain reaches LivingEntity.aiStep, which calls travel and then applyEffectsFromBlocks under !level().isClientSide() || isControlledByLocalInstance (LivingEntity.java 2418, 2457–2459, 2680, 2771–2776; Player.java 555 onward; LocalPlayer.java 638 and 795).
- Across the complete source trees, recordMovementThroughBlocks has two call sites in ServerGamePacketListenerImpl: one records a vehicle's accepted movement (1.21.2/1.21.3 line 452; 1.21.4 line 454), and one records a player after an accepted movement packet (1.21.2/1.21.3 line 930; 1.21.4 line 990). Neither belongs to ordinary LocalPlayer tick. ServerGamePacketListenerImpl SHA-256: 1.21.2/1.21.3 e4ad1ac2fa3236f659220c44dd4d5f63d9ef2ab5791bdabbfa2d229b14452a38; 1.21.4 c5467cc282f361b5ab5540066385240f847d101c68afdef8655908f3fb3b2c8b.

### 1.21.4

- Entity.move (627–712) still does not append a regular movement record; collideWithShapes (993 onward) retains the same Y-then-horizontal comparison sequence.
- applyEffectsFromBlocks(Vec3,Vec3) (751–764) appends the old-position-to-current segment, copies the list with List.copyOf, clears movementThisTick, then passes the copy to checkInsideBlocks. It snapshots and empties the raw record list before the consumer runs. This note concerns record transfer timing only; callback behavior is excluded.
- LocalPlayer.tick still calls super.tick, now behind a client-loaded gate (LocalPlayer.java 191–196). LivingEntity.aiStep applies block effects after travel (LivingEntity.java 2782–2787) under the same client/local-instance condition.

### 26.2 unobfuscated

- Entity.move (711–755) on the normal-physics path computes movement = collide(delta), then under movement.lengthSqr() > 1.0E-7 || delta.lengthSqr() - movement.lengthSqr() < 1.0E-7 captures current position and appends one Movement(pos,pos+movement,delta) before setting position. The no-physics branch updates position without recording. Production has moved into Entity.move: one accepted record per call carries the resolved displacement and original request as axis-order metadata; the record is not one entry per axis.
- Raw records are held in movementThisTick (ArrayDeque, initial size 100). addMovementThisTick (920–929) preserves append order except when size is already at least 100: it removes the first two entries, replaces them at the front with Movement(first.from(),second.to()), then appends the new record. This combined record has no axis-dependent original movement.
- applyEffectsFromBlocks() (903–914) clears finalMovementsThisTick, copies raw records into it in deque order, then clears the raw queue. If the queue is empty, it always adds Movement(oldPosition(),position()), including zero length. Otherwise it adds a terminal connector only when squared distance from the last record's to to current position exceeds 9.9999994E-11F; a smaller/equal gap is suppressed. applyEffectsFromBlocksForLastMovements (916–918) reuses the finalized list without draining/rebuilding the raw queue. The raw and finalized/deferred lists are distinct.
- A zero-length normal-physics move record can be retained: the append gate accepts a fully clipped-to-zero result when requested squared length minus actual squared length is below 1.0E-7, and accepts zero requested movement. An empty queue also leads to a zero-length fallback when old and current positions match. The no-physics branch queues no record.
- collideWithShapes (1244–1260) iterates Direction.axisStepOrder(movement), skips zero requested axis components, resolves each component against the box shifted by prior resolved components, and updates that component in the accumulated Vec3.
- checkInsideBlocks (1278–1306) walks finalized Movement entries in list order. When a record has axis metadata and a nonzero from-to delta, it visits nonzero components in Direction.axisStepOrder(axisDependentOriginalMovement), forming one intermediate from-to segment per component. Without metadata, or with a zero-length delta, it uses one from-to segment. Thus component order is distinct from raw record-list order.
- LocalPlayer.tick calls super.tick after the client-loaded gate (LocalPlayer.java 227–234). LivingEntity.tick dispatches virtual aiStep (LivingEntity.java 2765, 2804–2806); the LocalPlayer/Player super chain reaches LivingEntity.aiStep, which calls travel(input) and then applyEffectsFromBlocks under !level().isClientSide() || isLocalInstanceAuthoritative (LivingEntity.java 3043, 3140–3145; Player.java 453; LocalPlayer.java 767 and 914).

## Source-based interpretation

The ordinary LocalPlayer path reaches block-effect finalization after travel, while raw record production differs at these endpoints. In 1.21.2–1.21.4, ordinary move calls contribute no records; finalization appends old-position-to-current alongside any explicit records from packet handling. In 26.2, ordinary move calls queue each accepted resolved displacement with its original request; list order is preserved subject to compaction at 100, and finalization can interpret an axis-dependent record in ordered axis components. This is a source-path interpretation only. It makes no trajectory prediction, exact change-boundary claim between uninspected releases, or runtime-parity claim.

## Unresolved gates and next action

1.21.2 has a complete embedded per-source hash map and the inspected files match it, but its local corpus lacks a separate ready/source-manifest envelope and its provenance does not declare a top-level mapping option. The tree path is Mojmap and its mapping input is hashed, but formal identity still needs the source-preparation owner's acceptance of this provenance format. No source comparison is blocked by that caveat.

Next action: the master should attach this bounded evidence to the source-only coverage record and request an independent inventory review before any acceptance or pair-freeze claim.
