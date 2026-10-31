# Independent source review — relative move zero-delta coordinate retention (revision 2)

## Binding and inputs

- Verdict target: `.native-source/20261030/1152-1165-finish43/canonical-findings/1.16.1--1.16.2/relative-move-zero-delta-coordinate-retention.md`
- Bound draft SHA-256: `57843BA4C162B97DA18C526C34EC1E95A4897CAD7CA38E2E5FDC861CD13C2849`
- Cited packet report SHA-256: `957A85DBB8915B049139816ADCF467878F9F11975D3BB06734FD935085869354`
- Cited earlier independent report review SHA-256: `7136192863EF3E2E078914B4E5D3D90459D696B898BCC31A377CA2388CFAA16E`
- Canonical source root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\{1.16,1.16.1,1.16.2}\mojmap`
- In each version, all cited source files' computed SHA-256 values match `mojmap.sources.sha256`. The 1.16 `mojmap.ready.json` and provenance both identify the exact version ID as `1.16`; the ready metadata also binds the Mojmap source manifest hash. The 1.16.1 and 1.16.2 ready metadata likewise identify their exact IDs.

## Verdict

**ACCEPT this exact draft revision.** It corrects the earlier 1.16.1 `LivingEntity` line references, now identifies the oldest source as version ID `1.16` rather than `1.16.0`, and supplies matching `Entity.java` identities for the accumulator/storage evidence. The exact-source comparison supports the stated cutover: the long-accumulator implementation is present in 1.16 and 1.16.1, and 1.16.2 introduces the per-axis `updateEntityPosition(Vec3)` route. No additional source or claim correction is required for this bounded finding.

## Exact source identity checks

- `ClientPacketListener.java`: 1.16 and 1.16.1 `466d11a2b628c80e17885a0e85f25fee2ee6a05de33d9e71f309b8bc233a549d`; 1.16.2 `4d043b866c970910ef079334b0d506f7bc0f78f679e5225f11a0418bab7a72f9`.
- `Entity.java`: 1.16 and 1.16.1 `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576`; 1.16.2 `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`.
- `ClientboundMoveEntityPacket.java`: 1.16 and 1.16.1 `7bf671557d36296789f53e8b0b1d576bf946543b0d3388926bebffa2174099ec`; 1.16.2 `c787f142b1621edfa211b7293f851171562ca79d32299e76718593052a354db7`.
- `LivingEntity.java`: 1.16.1 `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26`; 1.16.2 `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`.

## Review notes

The 1.16 and 1.16.1 `ClientPacketListener.handleMoveEntity` bodies at `:638–658` share the same long-axis accumulation and whole-vector conversion. Their `Entity` declares `long xp/yp/zp`; `setPacketCoordinates(double,double,double)` initializes those values with `entityToPacket`/`Mth.lfloor(coordinate * 4096.0)`. Their packet class has `packetToEntity(long,long,long)` and no `updateEntityPosition(Vec3)` member.

In 1.16.2 `handleMoveEntity` at `:628–648` obtains a packet-coordinate vector, calls `updateEntityPosition`, stores its result, and dispatches `lerpTo`. The packet method's zero-delta arm returns the prior axis double unchanged; its nonzero arm floors to packet units, adds the short delta and divides by `4096.0`. `Entity` stores and returns the `Vec3` directly. This makes the boundary between 1.16.1 and 1.16.2 exact and source-confirmed.

The corrected `LivingEntity` references match: 1.16.1 `lerpTo` at `:2583–2590` and interpolation branch at `:2376–2385`; 1.16.2 `lerpTo` at `:2585–2592` and interpolation branch at `:2378–2387`. The existing review's LocalPlayer reachability and conditional finite off-grid teleport seeding remain supported by the exact endpoint receiver, entity setter, client-level lookup, and inherited control predicate. The finding retains the appropriate limitations on packet production, packet/tick ordering, and runtime parity.

The cited packet report and its review are still the stated 1.15.2→1.16.5 pair; they provide supporting context but do not establish this 1.16.1→1.16.2 boundary. This acceptance is based on the independently checked exact 1.16/1.16.1/1.16.2 sources above.

No product/wiki/backlog changes, build, tests, game launch, or decompilation were performed.
