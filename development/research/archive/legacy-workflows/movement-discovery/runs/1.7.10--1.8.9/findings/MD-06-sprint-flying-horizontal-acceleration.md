# MD-06: Sprinting doubles the flight movement input factor

- Older version A: 1.7.10
- Newer version B: 1.8.9
- Mechanic / coverage slice IDs: stages 2.2 and 3.4
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.7.10, 1.8.9]
- Runtime validation: not performed

## Paired evidence

- A artifact A; `../../../../decompiled_minecraft/1.7.10/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`; `PlayerEntity.moveRelative(float,float)`; lines 1236-1250; SHA-256 `350901699E449537372E1C4AF6A79AE256DE39638CDAD049DFC7800B6607791C`. When flying and not riding, it saves `speedInAir`, sets it to `abilities.getFlySpeed()`, calls the superclass movement method, dampens vertical velocity by `0.6`, and restores the field.
- B artifact B; `../../../../decompiled_minecraft/1.8.9/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`; `PlayerEntity.moveRelative(float,float)`; lines 1278-1292; SHA-256 `E66CB294FC93118148A444BBAFDF4DD57CBF66A23D69B1E8892CEFCCC690AB88`. The corresponding assignment is `abilities.getFlySpeed() * (isSprinting() ? 2 : 1)`; other save/call/dampen/restore steps remain in the method.
- A and B `PlayerAbilities.java:11,44-49` both default fly speed to `0.05F` and expose a setter. Hashes: A `0B6AD59AF31D6935E00B82BBDE28A2BF0F9619A9B1F301093F0721007D99CAEF`; B `B0350CA7819F995E4EDE358A9D71270CC7A073A9D8817CE218D363CCA7EA0CB0`.
- B consumer; `../../../../decompiled_minecraft/1.8.9/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`; `LivingEntity.moveRelative(float,float)`; lines 1123-1138; SHA-256 `082831C6578E3A70FA6CEA5B90BC3EEFC26678259B66334470DE22B90B5B0E4E`. In the airborne branch the temporary `speedInAir` value is used as the input factor.

## Source-level difference

Precondition: a player is airborne, has flight enabled, is not riding, and is sprinting. A applies the ability fly speed as the input factor. B applies twice the ability fly speed. Both restore the original `speedInAir` after the superclass movement call and multiply vertical velocity by `0.6` within this flight override.

## Reachability and dependencies

Local player input permits the held sprint-key branch while airborne when forward input and other sprint guards pass; flight permission satisfies the hunger-or-flight gate. `PlayerEntity.moveRelative` then selects the flying branch, and `LivingEntity.moveRelative` consumes the temporary factor in its airborne movement branch. The fly-speed value is stored in `PlayerAbilities` and may be externally set or synchronized; both releases use a `0.05F` default.

## Consequence and uncertainty

Source-proven: B doubles the horizontal movement input factor while flying and sprinting under the stated conditions. Inference: the same airborne input can add more horizontal velocity in B. No trajectory was tested. The introduction release is unknown within `(1.7.10, 1.8.9]`.

## Handoff

Preserve the sprint-dependent factor only in B's airborne flight path, along with the existing save/restore and vertical-velocity operations. Verify external fly-speed values separately. Runtime validation is deferred.
