# Versionnement du fork xDreams (Fabric 26.3)

La base upstream est conservée dans `gradle.properties` : `version=6.2.0`.
On ne la modifie que pour une vraie mise à jour de la version du mod en amont.

Pour **chaque nouveau commit construit par GitHub Actions**, la version du mod
dans `fabric.mod.json` et le nom du JAR reçoit un suffixe
`+xdreams.<8 premiers caractères du SHA du commit>`, par exemple :

`26.3-Fabric-6.2.0+xdreams.a1b2c3d4`

La même révision Git conserve le même code de build, même en cas de
recompilation : ce code identifie donc une **version du code**, pas un
numéro d'exécution GitHub Actions.

Ne pas publier ce JAR sur les pages Modrinth / CurseForge de l'auteur.
Il s'agit d'un fork personnel. Les fonctionnalités et correctifs doivent
être réalisés sur la branche `26.3`. Le futur port d'une autre version de
Minecraft doit utiliser une branche correspondant **uniquement** à cette
version de Minecraft.

Les commandes locales `./gradlew :Fabric:build` conservent le numéro de
version de base pour le développement. Pour simuler la version distribuée :
`./gradlew -Pversion="6.2.0+xdreams.<sha8>" :Fabric:build`.

Les branches utiles du dépôt sont `26.1.2` et `26.3`, chacune portant le
code adapté à sa version de Minecraft. Les branches `backup/*` et `tmp/*`
peuvent être supprimées quand les commits sont présents dans l'historique.
