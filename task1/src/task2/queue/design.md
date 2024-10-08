Pour réaliser la task 2, le QueueBroker va déléguer la gestion de la connexion au Broker de la task1.
De la  meme maniere le Message Broker va deleguer l'envoi des messages au Channel de la task1.

Pour le QueueBroker, les function connect et accepte vont simplement renvoyer aux fonctions du meme nom du Broker.

Pour le MessageQueue, le fait de devoir avoir l'entiereté du message oblige à adapter l'utilisation de Channel.
Pour cela send envoie d'abord 4 bytes pour donner à receive la longueur du contenu a lire.
Receive va acknowledge la longueur et la renvoyer au send comme confirmation (j'ai un doute si c'est réelement utile)
Le send va ensuite envoyer le contenu qui va etre recu par le receive. 