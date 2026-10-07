# F001: Input scaling and underwater sprint-forward cutoff

- Older version A: 1.13.2
- Newer version B: 1.14.4
- Mechanic / coverage slice IDs: local input and sprint gate; S001
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.13.2, 1.14.4]
- Runtime validation: not performed

## Paired evidence

- A manifest: `../../../build/movement-campaign-2026-10-07/ready/1.13.2/ornithe-feather/`; `KeyboardInput.java`::`tick()`, lines 13-50, SHA-256 `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a`; `LocalClientPlayerEntity.java`, sprint-forward consumers around lines 701, 726, 740, SHA-256 `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`.
- B manifest: `../../../build/movement-campaign-2026-10-07/ready/1.14.4/ornithe-feather/`; `KeyboardInput.java`::`tick(boolean,boolean)`, lines 13-26, SHA-256 `5932453a9e48e7a798ae1be1cd3a4bf660b6b3be43bb7e5dc22686b3c4a82526`; `LocalClientPlayerEntity.java`::`m_03985577()`, lines 961-964, SHA-256 `708af6a3880fb58b67bf4604a5509b351719a9c2a0c06a8ec261586435bf00ce`.

## Source-level difference

A applies the sneaking factor 0.3 to forward and sideways inputs whenever sneaking. B applies it when actual sneaking or its `bl` pose-related condition holds, unless spectator. B's submerged sprint predicate uses positive forward input `>1.0E-5F`; A's checked submerged predicate uses `movementForward >= 0.8F`. B retains `>=0.8F` for the ordinary non-submerged path. These are independent changed gates within this slice.

## Reachability and dependencies

Keyboard input is sampled in local player `mobTick` and the resulting movementForward feeds local sprint initiation/timer checks. B supplies pose-related `bl` and spectator state to the input method. Full sprint eligibility (including food/blindness reads) and all update order are not closed; those producer systems remain excluded from emulation.

## Consequence and uncertainty

Source proves the changed scale and submerged threshold. The threshold may permit sprint initiation with a smaller positive submerged forward input than A; resulting speed/position is not observed here. `bl`'s exact upstream pose state and full gates need closure in INV-TICK/INV-STATE.

## Handoff

Independent delta description; related finding IDs: none. Applicability is limited to the local player and tested input conditions. First changed release is unknown. Implementation/testing deferred.
