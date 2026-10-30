# Correction 2: post-acceleration order and velocity type

This supplement preserves both earlier glide-extension artifacts and corrects their statement that vertical drag precedes the later glide adjustments. In every checked release, the source order is:

1. Apply `velocityY += -0.08 + m * 0.06`.
2. If `velocityY < 0.0` and horizontal look length is positive, apply the dive correction to vertical velocity, then the corresponding horizontal components.
3. If pitch is negative, apply the pull-up correction to vertical velocity, then the corresponding horizontal components.
4. If horizontal look length is positive, apply the horizontal steering correction.
5. Apply horizontal drag (`velocityX *= 0.99F`, `velocityZ *= 0.99F`) and vertical drag (`velocityY *= 0.98F`).

Exact `LivingEntity.java` source lines for the default-gravity term and subsequent vertical corrections/drag are: 1.9: 1305, 1308, 1315, 1326; 1.9.4: 1318, 1321, 1328, 1339; 1.10.2: 1348, 1351, 1358, 1369; 1.11.2: 1404, 1407, 1414, 1425; 1.12/1.12.1/1.12.2: 1441, 1444, 1451, 1462. Lines use each release's `ornithe-feather/net/minecraft/entity/living/LivingEntity.java`.

`velocityY` is a `double` field declared in each paired `ornithe-feather/net/minecraft/entity/Entity.java` (1.9 line 95; 1.9.4 line 96; 1.10.2 line 97; 1.11.2 line 104; 1.12, 1.12.1, and 1.12.2 line 105). Its source-file SHA-256 values, in the same order, are `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a`, `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0`, `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77`, `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440`, and `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` (shared by 1.12 through 1.12.2). Each recomputed hash matches the corresponding source manifest or embedded provenance entry.
