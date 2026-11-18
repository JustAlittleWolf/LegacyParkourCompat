# 1.21.10–1.21.11 pose, size, and first refresh comparison

## Inputs and integrity

- Exact inputs: 1.21.10 and 1.21.11; Mojmap sources under decompiled_minecraft/<version>/mojmap/.
- Each version's mojmap.ready.json and mojmap.provenance.json agree on exact version ID and Mojmap namespace. Both identify Vineflower, Java 25 and successful completion. Ready markers identify client SHA-256 1.21.10 e65ca028bc58da12bf8413066c90ecd4d48c94fb351d9bbd22ecdff5c87f9482, 1.21.11 1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd. Both record artifact-manifest SHA-256 c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c.
- The source-manifest hashes match the ready markers: 1.21.10 4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1; 1.21.11 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555.
- Consumed source-file hashes match their manifest entries. 1.21.10: Player.java af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82; LivingEntity.java b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66; Entity.java 8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18. 1.21.11: Player.java 8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81; LivingEntity.java 19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8; Entity.java 32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3.

## Bounded result

For ordinary awake player pose transitions, with crouch input held equal, the Player.updatePlayerPose, getDesiredPose, and canPlayerFitWithinBlocksAndEntitiesWhen bodies are byte-for-byte equal after UTF-8 extraction (SHA-256 respectively aa85522854bfa67518683e9e21bcddf1ab14b19cc305f6ff69a91c0e0ae3a2d5, 50d6ec94fe990c9dfbbe02c3c1687f5962965af44a77c26f524d89c1f3a70b60, 62ac4e2bbf754c5668843b5d547fad0c2f0908e707ef525a63023f160b4c3220). Thus desired-pose priority, fit gates, fallback selection, and pose writer call are equal.

Entity.setPose is equal (body SHA-256 191b1317ee9cb9864d2072320c05018997af02574539ebac760e232b3a6774fa). Entity.refreshDimensions is equal (body SHA-256 f8afbd0828402b1dba06bb941e3a75528989a8f775af0c997aa7806e2540b96e): it reads current dimensions, obtains dimensions for current pose, writes dimensions and eye height, then calls reapplyPosition, which recreates the bounding box at the current position. The same-pose dimension paths are equal: LivingEntity.getDimensions and getDefaultDimensions hashes are 22e827a2143de260506ef118dccda1ea11cf5ab1365f57597dc4e6b28716321c and 10e0f8e429095e6c837607e29310195e5f6ce40483ba53a32ce1b0dc66d2732a. For this ordinary awake pose set, default dimensions derive from type dimensions and age scale, not crouch pose; sleeping's special dimensions are outside this slice. Collision query internals and providers were not inspected.

One reachable first-refresh difference remains in Entity.reapplyPosition. 1.21.10 directly calls setPos at the unchanged position. 1.21.11 first clears lastKnownPosition, then calls setPos. The 1.21.11 Entity tick calls computeSpeed, whose body seeds a null lastKnownPosition from current position before calculating lastKnownSpeed. Since 1.21.10 has no corresponding lastKnownPosition or computeSpeed tracker, the later release records a newly introduced zero displacement sample after a pose-triggered refresh. The refresh still produces the same dimensions, eye height, and AABB for the same position and pose. This finding concerns the cache invalidation in the requested refresh/reposition path; no changed player trajectory is established.

## Consumed body digests

The body hashes above were computed over each exact source member from its signature through its matching closing brace, UTF-8 encoded. Entity.reapplyPosition: 1.21.10 0936844f00abbc229f417f42ad4cd9b8b9643d39bade41822f3f1b514a9eb4e6; 1.21.11 3b222968d1fe6e05ac8b85f16234245fb42d9fe26a31e9b55754274b34ad454b. The 1.21.11 Entity.computeSpeed body was consulted to confirm the cache consequence (call at line 487; method at line 540): SHA-256 eaeacea9d73df7fe292711c6d424b1d41d8cf6cd1e53544b2df14727b6563db6. The older Entity has no matching member or lastKnownPosition field.

## Residual boundary

Compared: awake ordinary desired-pose choice/writer, same crouch input and same fit outcomes; current-pose dimensions and eye-height writes; pose-synced first dimensions refresh, reposition and AABB refresh. Excluded: sprint/sneak state writers and timers; swimming/crawling LUT or rotation; flight, climbing, riding and external responses; collision-query internals/providers; other Entity movement and cached-speed consumers. No test, launch, build, wiki, product, or Git-history check was performed.
