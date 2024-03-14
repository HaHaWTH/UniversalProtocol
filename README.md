# Universal Protocol

**Provides mod protocol supprort for your spigot/paper server!**

[![Available on SpigotMC](https://img.shields.io/badge/Available%20on%20SpigotMC-orange?style=for-the-badge&logo=SpigotMC&logoColor=FFFFFF)](https://www.spigotmc.org/resources/✨universalprotocol✨-mod-protocol-support⚙%EF%B8%8F-1-13-1-20.115631/)

## Currently supported mod protocols:

- [ChatImage-Send images in chat(Forge/Fabric)](https://www.curseforge.com/minecraft/mc-mods/chatimage)

- [AppleSkin-Show saturation status(Forge/Fabric)](https://www.curseforge.com/minecraft/mc-mods/appleskin)

- [XareoWorld/MiniMap-Maps](https://www.curseforge.com/minecraft/mc-mods/xaeros-minimap)

## Configuration:

```yaml
appleSkin:
    # Enable AppleSkin Protocol
    # 是否启用AppleSkin协议支持
    appleSkinProtocol: true
chatImage:
    # Enable ChatImage Protocol
    # 是否启用ChatImage协议支持
    chatImageProtocol: true
xaeroMap:
    # Enable XaeroMap Protocol
    # 是否启用XaeroMap协议支持
    xaeroMapProtocol: true
    # XaeroMap server id
    # XaeroMap的服务器唯一标识符
    serverIdentifier: 3282861(This will generate randomly at first time)
```
