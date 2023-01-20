package me.alexirving.lib.command.core.argument

class ArgumentParser<U> {
    private val mapping = mutableMapOf<Class<*>, ArgumentResolver<U, *>>()

    /**
     * Registers a new resolver.
     * @param clazz The class to resolve
     * @param resolver Method to resolve the
     */
    fun <T> register(clazz: Class<*>, resolver: (sender: U, text: String) -> T?) {
        mapping[clazz] = object : ArgumentResolver<U, T>("test", clazz) {
            override fun resolve(sender: U, text: String) = resolver(sender, text)
        }
    }

    fun resolve(clazz: Class<*>, sender: U, text: String) = mapping[clazz]?.resolve(sender, text)
    fun resolveAsync(clazz: Class<*>, sender: U, text: String, resolved: (resolved:Any?) -> Unit){
        mapping[clazz]?.resolve(sender, text){
            resolved(it)
        }
    }

}